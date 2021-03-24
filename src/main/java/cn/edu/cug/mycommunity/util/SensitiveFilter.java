package cn.edu.cug.mycommunity.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    //替换字符
    private static final String REPLACEMENT = "**";

    //根节点
    private TrieNode rootNode = new TrieNode();

    @PostConstruct
    public void init(){
        try(
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                ){
            String keyword;
            while((keyword = reader.readLine()) != null){
                //添加到前缀树
                this.addKeyword(keyword);
            }

        }catch (IOException e){
            logger.error("加载敏感词文件失败",e.getMessage());
        }

    }

    /**
     * 过滤敏感词
     * @param text 待过滤文本
     * @return
     */
    public String filter(String text){
        if(StringUtils.isBlank(text))
            return null;

        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;
        StringBuilder  sb = new StringBuilder();
        while(position < text.length()){
            char c = text.charAt(position);
            //跳过符号
            if(isSymbol(c)){
                if(tempNode == rootNode){
                    sb.append(c);
                    begin++;
                }
                position++;
                continue;
            }
            //检查下级节点
            tempNode = tempNode.getSubNode(c);
            if(tempNode == null){
                //不是敏感词
                sb.append(text.charAt(begin));
                ++begin;
                position = begin;
                tempNode = rootNode;
            }else if(tempNode.isKeywordEnd()){
                //发现敏感词
                sb.append(REPLACEMENT);
                begin = ++position;
                tempNode = rootNode;
            }else{
                position++;
            }
        }
        sb.append(text.substring(begin));
        return sb.toString();

    }

    //判断是否为符号
    private boolean isSymbol(Character c){
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80  || c > 0x9FFF);
    }
    //将敏感词添加到前缀树中
    private void addKeyword(String keyword){
        TrieNode tempNode = rootNode;
        for(int i = 0; i < keyword.length();i++){
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);
            if(subNode == null){
                subNode = new TrieNode();
                tempNode.addSubNode(c,subNode);
            }

            tempNode = subNode;
            //设置结束表示
            if(i == keyword.length()-1){
                tempNode.setKeywordEnd(true);
            }
        }
    }
    //前缀树
    private class TrieNode{
        private boolean isKeywordEnd = false;
        //子节点
        private Map<Character,TrieNode> subNodes = new HashMap<>();
        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        //添加子节点
        public void addSubNode(Character c,TrieNode node){
            subNodes.put(c,node);
        }
        //获取子节点
        public TrieNode getSubNode(Character c){
            return subNodes.get(c);
        }
    }




}
