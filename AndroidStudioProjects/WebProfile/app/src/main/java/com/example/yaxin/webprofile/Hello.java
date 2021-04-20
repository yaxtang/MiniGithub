package com.example.yaxin.webprofile;

/**
 * Created by Yaxin on 11/7/2017.
 */
/*
public class Hello {
    import numpy as np
import scipy as sc

    def contains(sequence, pattern): # sequence = the sequence to handle, pattern = determine whether this pattern is in this sequence
            element_len = len(pattern)
    if element_len == 0:
            return False;#undefined behavior return False for robusticity
            appears = np.zeros(element_len)
    for element in sequence:
            if element in pattern:
    index = pattern.index(element)
    appears[index] += 1
            for result in appears:
            if result == 0:
            return False;
    return True;

    def is_frequent_pattern(data, pattern, theta, epsilon):   # data = data to handle, pattern = determine whether this pattern is a frequent
    count_res = 0
    count_all = 0
    required_count = len(data) * theta
    for sequence in data:
            if contains(sequence, pattern):
    count_all += 1
            if outlier_ratio(sequence, pattern, epsilon):
    count_res += 1
            if(count_res > required_count):
            return[True, True]
            return [(count_res / len(data)) >= theta, (count_all / len(data)) >= theta]


    def outlier_ratio(sequence, pattern, epsilon):
            if len(pattern) == 0:
            return False # Empty pattern exception, return large ratio for robusticity
    ratio_2d_array = []
            for i in range(len(sequence)):
            ratio_2d_array.append([])
            for j in range(len(sequence)):
    ratio_2d_array[i].append(len(sequence));
    # ratio_2d_array[i][j] = the outlier ratios start from i end at j
    for i in range(len(sequence)):
            # Here i = start index
            outlier_num = 0
    appeared_array = np.zeros(len(pattern))
            for j in range(i, len(sequence)):
            if sequence[j] in pattern:
    appeared_array[pattern.index(sequence[j])] += 1
            else:
    outlier_num += 1
            if 0 in appeared_array:
            continue;
            else:
    ratio_2d_array[i][j] = outlier_num / len(pattern)
                if ratio_2d_array[i][j] <= epsilon:
            return True
    # Pick the minimum ratio among all of the tuples
    return False

    def getRest(list_of_symbols, pattern):
    maxSymbol = -1;
    for a in pattern:
            if a > maxSymbol:
    maxSymbol = a;
    result = [];
    for b in list_of_symbols:
            if b > maxSymbol:
            result.append(b);
    return result;

    file = open("data2.txt", "r")
    data_array = []
    count = 0
    condition_line = list(map(float, file.readline().split(",")))
    theta = condition_line[0]
    epsilon = condition_line[1]
    alphabet = []
            while True:
    curr_str = file.readline()
            if curr_str == "":
            break;
    data_array.append([])
    data_array[count] = list(map(int, curr_str.split(",")))
            for i in data_array[count]:
            if i not in alphabet:
            alphabet.append(i)
    count+=1
    good_pattern = []
    result_pattern = []
            for i in range (len(alphabet)):
            good_pattern.append([])
            for i in range (len(alphabet)):# i represents length of the pattern
    if i == 0: # 1-length pattern
        for j in range(len(alphabet)):# j represents the initial 1-length patterns' element
    testPattern = []
            testPattern.append(alphabet[j])
    two_results = is_frequent_pattern(data_array, testPattern, theta, epsilon)
            if two_results[1]:
    good_pattern[0].append(testPattern)
            if two_results[0]:
            result_pattern.append(testPattern)
            else:
            for pre_pattern in good_pattern[i-1]:
    currSymbols = getRest(alphabet, pre_pattern)
            for addedSymbol in sorted(list(currSymbols)):
    testPattern = []
            for cpy in pre_pattern:
            testPattern.append(cpy);
                testPattern.append(addedSymbol);
    two_results = is_frequent_pattern(data_array, testPattern, theta, epsilon)
                if(two_results[1]):
    good_pattern[i].append(testPattern)
                if(two_results[0]):
            result_pattern.append(testPattern)

    print(result_pattern)




}
*/