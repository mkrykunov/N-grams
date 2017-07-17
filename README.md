# N-grams
This mini project was inspired by the Programming Assignment 2
from the Neural Networks for Machine Learning course taught by 
Geoffrey Hinton at Coursera. In this assignent, it is necessary 
to build a neural net language model that will learn to predict 
the next word, given previous three words. The data set consists 
of 4-grams. However, first of all, an algorithm for the generation
of the 4-grams was not provided. Second of all, there are repetitions 
in 4-grams, so that there are 4-grams in the validation and test 
sets that are also present in the training set. The program based 
on 'N-gram.java' file will create all those 4-grams from raw sentences 
text file that is also provided in the above mentioned course. 
However, there is also an option in this program to eliminate 
repetitions, so that all N-grams are unique.

The 'raw_sentences.txt' file has the following format:

This is a first sentence .

This is a second sentence .

...

The 'vocabulary.txt' file has the following format:

word1

word2

...

The 'ngramInt.txt' has the following format:

num1 num2 num3 num4

num2 num3 num4 num5

...
