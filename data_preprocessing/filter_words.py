# pip3 install wordfreq
# use this tool to get the frequenct of each word
from wordfreq import zipf_frequency


# read the words
with open("five_letter_words.txt") as f:
    all_words = [w.strip().lower() for w in f if w.strip()]


# set different levels
easy_level = [
    w for w in all_words if zipf_frequency(w, "en") >= 5.0
]

medium_level = [
    w for w in all_words if zipf_frequency(w, "en") >= 4.0 and
    zipf_frequency(w, "en") < 5.0
]

hard_level = [
    w for w in all_words if zipf_frequency(w, "en") >= 3.0 and
    zipf_frequency(w, "en") < 4.0
]

expert_level = [
    w for w in all_words if zipf_frequency(w, "en") < 3.0
]


def write_txt(level, file_name):
    with open(file_name, "w") as f:
        for w in sorted(level):
            f.write(w + "\n")
            
    print(file_name + " Scucessfully Saved!!!")
    print("There are {num} words.\n".format(num=len(level)))
    

write_txt(easy_level, "easy_level_words.txt")
write_txt(medium_level, "medium_level_words.txt")
write_txt(hard_level, "hard_level_words.txt")
write_txt(expert_level, "expert_level_words.txt")


## Results:
##easy_level_words.txt Scucessfully Saved!!!
##There are 183 words.
##
##medium_level_words.txt Scucessfully Saved!!!
##There are 666 words.
##
##hard_level_words.txt Scucessfully Saved!!!
##There are 1352 words.
##
##expert_level_words.txt Scucessfully Saved!!!
##There are 3356 words.
