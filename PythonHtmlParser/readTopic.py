#coding=utf-8
import  xml.dom.minidom

#打开xml文档
dom = xml.dom.minidom.parse('dynamic-domain-2016-truth-data.xml')

f = file("topic_name","w+")

#得到文档元素对象
root = dom.documentElement

itemList = root.getElementsByTagName('topic')
for item in itemList:
    a = item.getAttribute("id")
    b = item.getAttribute("name")
    f.write(a)
    f.write("\n")
    f.write(b)
    f.write("\n")
print b

