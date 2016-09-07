#! /usr/bin/python
# -*- coding:utf-8 -*-
'''
Created on 2016-07-26
@author: Jerry
'''
import re
import nltk


class FilterTag():
    def __init__(self):
        pass

#     def replaceCharEntity(self,htmlStr):
#         '''
#         替换html中常用的字符实体
#         使用正常的字符替换html中特殊的字符实体
#         可以添加新的字符实体到CHAR_ENTITIES 中
#     CHAR_ENTITIES是一个字典前面是特殊字符实体  后面是其对应的正常字符
#         :param htmlStr:
#         '''
#         self.htmlStr = htmlStr
#         CHAR_ENTITIES={'nbsp':' ','160':' ',
#                 'lt':'<','60':'<',
#                 'gt':'>','62':'>',
#                 'amp':'&','38':'&',
#                 'quot':'"','34':'"',}
#         re_charEntity=re.compile(r'&#?(P\w+);')
#         sz=re_charEntity.search(htmlStr)
#         while sz:
#             entity=sz.group()#entity全称，如>
#             key=sz.group('name')#去除&;后的字符如（" "--->key = "nbsp"）去除&;后entity,如>为gt
#             try:
#                 htmlStr= re_charEntity.sub(CHAR_ENTITIES[key],htmlStr,1)
#                 sz=re_charEntity.search(htmlStr)
#             except KeyError:
#                 #以空串代替
#                 htmlStr=re_charEntity.sub('',htmlStr,1)
#                 sz=re_charEntity.search(htmlStr)
#         return htmlStr
#
#     def replace(self,s,re_exp,repl_string):
#         return re_exp.sub(repl_string)
#
#     def strip_tags(self,htmlStr):
#         '''
#         使用HTMLParser进行html标签过滤
#         :param htmlStr:
#         '''
#         self.htmlStr = htmlStr
#
#         re_cdata=re.compile('//<!--\[CDATA\[[^-->]*//\]\]>',re.I) #匹配CDATA
#         re_script=re.compile('<\s*script[^>]*>[^<]*<\s*/\s*script\s*>',re.I)#Script
#         re_style=re.compile('<\s*style[^>]*>[^<]*<\s*/\s*style\s*>',re.I)#style
#         re_h=re.compile('<!--?\w+[^-->]*>')#HTML标签
#         re_comment=re.compile('<!--[^>]*-->')#HTML注释
#         s=re_cdata.sub('',htmlStr)#去掉CDATA
#         s=re_script.sub('',s) #去掉SCRIPT
#         s=re_style.sub('',s)#去掉style
#         s=re_h.sub('',s) #去掉HTML 标签
#         s=re_comment.sub('',s)#去掉HTML注释
#         # re_cdata=re.compile('//<!\[CDATA\[[^>]*//\]\]>',re.I) #匹配CDATA
#         # re_script=re.compile('<\s*script[^>]*>[^<]*<\s*/\s*script\s*>',re.I)#Script
#         # re_style=re.compile('<\s*style[^>]*>[^<]*<\s*/\s*style\s*>',re.I)#style
#         # re_comment=re.compile('<!--[^>]*-->')#HTML注释
#         # re_h=re.compile('</?\w+[^>]*>')#HTML标签
#         #
#         # htmlStr =re_cdata.sub('',htmlStr)#去掉CDATA
#         # htmlStr =re_script.sub('',htmlStr) #去掉SCRIPT
#         # htmlStr =re_style.sub('',htmlStr)#去掉style
#         # htmlStr =re_comment.sub('',htmlStr)#去掉HTML注释
#         # htmlStr =re_h.sub('',htmlStr) #去掉HTML 标签
#
#         s = s.strip()
#         s = self.replaceCharEntity(s)
#
#         result = []
#         parser = HTMLParser()
#         parser.handle_data = result.append
#         parser.feed(s)
#         parser.close()
#         result = ''.join(result)
#         return  ' '.join(result.split())
#
#     def stripTagSimple(self,htmlStr):
#         '''
#         最简单的过滤html <>标签的方法    注意必须是<任意字符>  而不能单纯是<>
#         :param htmlStr:
#         '''
#         self.htmlStr = htmlStr
# #         dr =re.compile(r'<[^>]+>',re.S)
#         dr = re.compile(r'<!--?\w+[^-->]*>',re.S)
#         htmlStr =re.sub(dr,'',htmlStr)
#         return  htmlStr


    def rm_stopword_stem(self, s):
        article = nltk.word_tokenize(unicode(' '.join(s.split()).lower(),"utf-8"))
        english_punctuations=[',','.',':',';','?','(',')','[',']','&','!','*','@','#','$','%',"“","”","‘","’","'s"]
        result = ""
        st = nltk.PorterStemmer()
        for word in article:
            stem_word = st.stem(word)
            if stem_word not in english_punctuations:
                if stem_word not in nltk.corpus.stopwords.words('english'):

                    result += stem_word
                    result += ' '
        return result

    def solveSentence(self,s):
        '''
        :param s: 需要进行自然语言处理的html
        :return: 处理完成去tag经过stemming去停词之后的句子
        '''
       # print s
        print self.beautifultry(s)
       # print self.rm_stopword_stem(self.stripTagSimple(s))
        return self.rm_stopword_stem(self.strip_tags(s))


if __name__=='__main__':
    s = file('0trec.txt').read()
    filters = FilterTag()
    print filters.strip_tags(s)
    print filters.rm_stopword_stem(filters.strip_tags(s))



