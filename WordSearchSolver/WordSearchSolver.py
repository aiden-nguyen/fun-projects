def main():
  puzzle = str(input())
  words = str(input())
  display_puzzle(puzzle)
  words += " "
  word = ""
  for i in words:
    if i != " ":
      word += i
    else:
      find_words(puzzle, word)
      word = ""



def display_puzzle(puzzle) -> str:
  width = int(len(puzzle) ** (1 / 2))
  display = ""
  n = 0
  for i in range(width):
    for j in range(width):
        if n < len(puzzle):
            display += puzzle[n]
            n += 1
        else:
            break
    display = display + ("\n")
  print(display)



def find_words(puzzle, word):
  find_forward(puzzle, word)
  find_backward(puzzle, word)
  find_down(puzzle, word)
  find_up(puzzle, word)
  forward_downward_columns(puzzle, word)
  backward_upwards_columns(puzzle, word)
  forward_downward_rows(puzzle, word)
  backward_upwards_rows(puzzle, word)
  backward_downward_columns(puzzle, word)
  forward_upward_columns(puzzle, word)
  backward_downward_rows(puzzle, word)




def find_forward(puzzle, word):
  testline = ""
  width = int(len(puzzle) ** (1 / 2))
  n = 0
  column = 0
  for i in range(width):
    for j in range(width):
      testline += puzzle[n]
      n += 1
    column = testline.find(word)
    if testline.find(word) >= 0:
      print (f"{word:>{width}}: [FF] ({i}, {column})")
    testline = ""



def find_backward(puzzle, word):
  testline = ""
  width = int(len(puzzle) ** (1 / 2))
  n = len(puzzle) - 1
  column = 0
  beep = 0
  for i in range(width):
    for j in range(width):
      testline += puzzle[n]
      n = n - 1
    column = testline.find(word)
    if testline.find(word) >= 0:
      column = abs(column - (width - 1))
      beep = abs(i - (width - 1))
      print (f"{word:>{width}}: [BB] ({beep}, {column})")
    testline = ""



def transpose_string(puzzle, width):
    n = 0
    z = 0
    boop = ""
    transposed = ""
    for i in range(width):
        for j in range(n, z + width):
            boop += puzzle[n]
            n += width
        transposed += boop
        boop = ""
        if len(transposed) < len(puzzle):
            z += 1
            n = z
    return transposed



def find_up(puzzle, word):
  width = int(len(puzzle) ** (1 / 2))
  puzzle = transpose_string(puzzle, width)
  testline = ""
  n = len(puzzle) - 1
  column = 0
  beep = 0
  for i in range(width):
    for j in range(width):
      testline += puzzle[n]
      n = n - 1
    column = testline.find(word)
    if testline.find(word) >= 0:
      column = abs(column - (width - 1))
      beep = abs(i - (width - 1))
      print (f"{word:>{width}}: [UU] ({column}, {beep})")
    testline = ""





def find_down(puzzle, word):
  width = int(len(puzzle) ** (1 / 2))
  puzzle = transpose_string(puzzle, width)
  testline = ""
  n = 0
  column = 0
  for i in range(width):
    for j in range(width):
      testline += puzzle[n]
      n += 1
    column = testline.find(word)
    if testline.find(word) >= 0:
      print (f"{word:>{width}}: [DD] ({column}, {i})")
    testline = ""



def reverse_string(chars: str) -> str:
    reverse = ""
    for char in chars:
        reverse = char + reverse
    return reverse



def forward_downward_columns(puzzle, word):
    width = int(len(puzzle) ** (1 / 2))
    testline = ""
    previous = width
    beep = 0
    n = 0
    x = 0
    for j in range(width):
        for n in range(width):
            if j + ((width * x) + n ) < len(puzzle):
                if len(testline) < previous:
                    testline += puzzle[j + ((width * x)) + n]
                    beep = j + (width * x) + n
                    x += 1
        if testline.find(word) >= 0:
          beep = (beep - ((len(testline) - testline.find(word) - 1) * width) - 
          (len(testline) - 1 - testline.find(word)))
          print (f"{word:>{width}}: [FD] ({beep // width}, {beep % width})")
        previous = previous - 1
        testline = ""
        x = 0



def forward_downward_rows(puzzle, word):
    width = int(len(puzzle) ** (1 / 2))
    testline = ""
    previous = width
    beep = 0
    n = 0
    x = 0
    for j in range(0, len(puzzle) - width, width):
        for n in range(width):
            if (j + ((width * x) + n ) < len(puzzle) and 
            (len(testline) < previous)):
                testline += puzzle[j + ((width * x)) + n]
                beep = j + (width * x) + n
                x += 1
        if testline.find(word) >= 0:
          beep = (beep - ((len(testline) - testline.find(word) - 1) * width) -
          (len(testline) - 1 - testline.find(word)))
          print (f"{word:>{width}}: [FD] ({beep // width}, {beep % width})")
        previous = previous - 1
        testline = ""
        x = 0



def backward_upwards_columns(puzzle, word):
    width = int(len(puzzle) ** (1 / 2))
    testline = ""
    previous = width
    beep = 0
    x = 0
    for j in range(width):
        for n in range(width):
            if j + ((width * x) + n ) < len(puzzle):
                if len(testline) < previous:
                    testline += puzzle[j + ((width * x)) + n]
                    beep = j + (width * x) + n
                    x += 1
        reverseline = reverse_string(testline)
        if reverseline.find(word) >= 0:
          beep = (beep - (reverseline.find(word) * width) -
          (reverseline.find(word)))
          print (f"{word:>{width}}: [BU] ({beep // width}, {beep % width})")
        previous = previous - 1
        testline = ""
        x = 0



def backward_upwards_rows(puzzle, word):
    width = int(len(puzzle) ** (1 / 2))
    testline = ""
    previous = width
    beep = 0
    x = 0
    for j in range(0, len(puzzle) - width, width):
        for n in range(width):
            if j + ((width * x) + n ) < len(puzzle):
                if len(testline) < previous:
                    testline += puzzle[j + ((width * x)) + n]
                    beep = j + (width * x) + n
                    x += 1
        reverseline = reverse_string(testline)
        if reverseline.find(word) >= 0:
          beep = (beep - (reverseline.find(word) * width) -
          (reverseline.find(word)))
          print (f"{word:>{width}}: [BU] ({beep // width}, {beep % width})")
        previous = previous - 1
        testline = ""
        x = 0



def backward_downward_columns(puzzle, word):
    width = int(len(puzzle) ** (1 / 2))
    testline = ""
    previous = 0
    beep = 0
    n = 0
    x = 0
    for j in range(width):
        for n in range(width):
            if j + ((width * x) - n ) < len(puzzle):
                if len(testline) < previous + 1:
                    testline += puzzle[j + ((width * x)) - n]
                    beep = j + ((width * x) - n)
                    x += 1
        if testline.find(word) >= 0:
          beep = (beep - ((len(testline) - testline.find(word) - 1) 
          * width) + (len(testline) - testline.find(word) - 1))
          print (f"{word:>{width}}: [BD] ({beep // width}, {beep % width})")
        previous = previous + 1
        testline = ""
        x = 0



def forward_upward_columns(puzzle, word):
    width = int(len(puzzle) ** (1 / 2))
    testline = ""
    previous = 0
    beep = 0
    x = 0
    for j in range(width):
        for n in range(width):
            if j + ((width * x) - n ) < len(puzzle):
                if len(testline) < previous + 1:
                    testline += puzzle[j + ((width * x)) - n]
                    beep = j + ((width * x) - n)
                    x += 1
        reverseline = reverse_string(testline)
        if reverseline.find(word) >= 0:
          beep = (beep - (reverseline.find(word) * width) +
          (reverseline.find(word)))
          print (f"{word:>{width}}: [FU] ({beep // width}, {beep % width})")
        previous = previous + 1
        testline = ""
        x = 0



def backward_downward_rows(puzzle, word):
    width = int(len(puzzle) ** (1 / 2))
    testline = ""
    previous = width
    beep = 0
    x = 0
    for j in range(width - 1, len(puzzle) - width, width):
        for n in range(width):
            if j + ((width * x) - n ) < len(puzzle):
                if len(testline) < previous + 1:
                    testline += puzzle[j + ((width * x)) - n]
                    beep = j + ((width * x) - n)
                    x += 1
        if testline.find(word) >= 0:
          beep = (beep - ((len(testline) - testline.find(word) - 1) * width) +
          (len(word) - testline.find(word) - 1))
          print (f"{word:>{width}}: [BD] ({beep // width}, {beep % width})")
        previous = previous - 1
        testline = ""
        x = 0
