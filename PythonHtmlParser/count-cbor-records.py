#!/usr/bin/env python2.7

import cbor
import sys
import gzip

def count_records(infile):
    if infile.endswith('.gz'):
        fp = gzip.open(infile, 'r')
    else:
        fp = open(infile, 'r')

    count = 0
    try:
        while (1):
            cbor.load(fp)
            count += 1
    except EOFError:
        return count

total = 0

for infile in sys.argv[1:]:
    count = count_records(infile)
    print infile, count
    total += count
    
if total != count:
    print "total", total
