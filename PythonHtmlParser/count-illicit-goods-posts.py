#!/usr/bin/env python2.7

import cbor
import sys
import gzip

def count_records(infile):
    if infile.endswith('.gz'):
        fp = gzip.open(infile, 'r')
    else:
        fp = open(infile, 'r')

    posts = 0
    threads = 0
    try:
        while (1):
            thread = cbor.load(fp)
            threads += 1
            features = thread.get(u'features')
            if features:
                items = features.get('items')
                if items:
                    posts += len(items)
    except EOFError:
        return threads, posts

total_threads = 0
total_posts = 0

for infile in sys.argv[1:]:
    threads, posts = count_records(infile)
    print infile, threads, posts
    total_threads += threads
    total_posts += posts
    
if total_threads != threads:
    print "total", total_threads, total_posts
