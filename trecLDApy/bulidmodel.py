#-*- coding:utf-8 -*-
import lda
from sklearn.feature_extraction.text import CountVectorizer
import numpy as np
class  iniModel:
    def bulidModel(self,filename,topicwordnum):
        corpus = []
        vocab = []
        for line in open(filename, 'r').readlines():
            print line
            corpus.append(line.strip())
            vocab +=line.split(" ")
            #print corpus
            #将文本中的词语转换为词频矩阵 矩阵元素a[i][j] 表示j词在i类文本下的词频
        vectorizer = CountVectorizer()
        print vocab
        print vectorizer
        X = vectorizer.fit_transform(corpus)
        analyze = vectorizer.build_analyzer()
        weight = X.toarray()
        print len(weight)
        print (weight[:5, :5])
        model = lda.LDA(n_topics=5, n_iter=500, random_state=1)
        model.fit(np.asarray(weight))
        topic_word = model.topic_word_
        n_top_words = topicwordnum

        for i, topic_dist in enumerate(topic_word):
            topic_words = np.array(vocab)[np.argsort(topic_dist)][:-(n_top_words+1):-1]
            print('Topic {}: {}'.format(i, ' '.join(topic_words)))
        doc_topic = model.doc_topic_
        print("type(doc_topic): {}".format(type(doc_topic)))
        print("shape: {}".format(doc_topic.shape))
        #输出前10篇文章最可能的Topic
        label = []
        for n in range(20):
            topic_most_pr = doc_topic[n].argmax()
            label.append(topic_most_pr)
            print("doc: {} topic: {}".format(n, topic_most_pr))


p = iniModel()
p.bulidModel("text.txt",8)

