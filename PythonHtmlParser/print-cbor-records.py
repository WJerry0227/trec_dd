#!/usr/bin/env python2.7
# coding=utf-8
import cbor
import sys
import os
import gzip
import pprint
import tagfilter

filters = tagfilter.FilterTag()

pp = pprint.PrettyPrinter(indent=4)
filePath = 'data/polar/'
listfile = os.listdir(filePath)

#infile = "ebola-web-01-2016-1.cbor.gz"
n = 0
for i in listfile:
    infile = filePath + i
    if i.endswith('.gz'):
        fp = gzip.open(infile, 'r')
    else:
        fp = open(sys.argv[1], 'r')

    f = file(str(n)+"trec_polar_html.txt","w+")
    n += 1
    try:
        while 1:
            r = cbor.load(fp)
            #pp.pprint(r)
            f.write("<DOC>\n<DOCNO>\n")
            f.write(r["key"])
            f.write("\n</DOCNO>\n")
            f.write("<DOCCONTENT>\n")
            content = r["response"]["body"].encode('utf-8')
            #s = filters.solveSentence(content).encode('utf-8')
            #f.write(s.encode('utf-8'))
            f.write(content)
            f.write("\n</DOCCONTENT>")
            f.write("\n</DOC>\n")
            f.write("\n")

    except EOFError:
        continue
   # except:
    #     print >>sys.stderr, "Error reading", infile, sys.exc_info()[0]
    finally:
        f.close()


