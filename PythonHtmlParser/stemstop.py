import tagfilter
import os

filters = tagfilter.FilterTag()
n = 1
filePath = 'data/polarText/'
listfile = os.listdir(filePath)
for i in listfile:
    infile = filePath + i
    f = open(infile,"r")
    outf = file(str(n)+"polarstem.txt","w+")
    n+=1
    outf.write('<DOCS>\n')
    line = f.readline()
    while line:

        if line == "<DOC>\n":
            line = f.readline()
            line = f.readline()
            outf.write("<DOC>\n<DOCNO>\n")
            outf.write(line)
            outf.write("</DOCNO>\n")
            outf.write("<DOCCONTENT>\n")
            line = f.readline()
            line = f.readline()
            line = f.readline()
            lint = f.readline()
            content = filters.rm_stopword_stem(line)
            outf.write(content.encode('utf-8'))
            outf.write('\n')
            outf.write('</DOCCONTENT>\n')
            outf.write('</DOC>\n')
            outf.write('\n')

        line = f.readline()
    outf.write('</DOCS>')