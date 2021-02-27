def printpuzzle(puzzle):
  for row in puzzle:
    for i in range(len(row) - 1):
      print(row[i], end = ' ')
    for j in range(len(row) - 1, len(row)):
      print(row[j])
    #print()


def getcoords(cell, n):
  return (cell // n, cell % n)



def removedupes(duplicate): 
  res = [] 
  for num in duplicate: 
    if num not in res: 
      res.append(num) 
  return res



def transpose(it):
  puz = [[0] * len(it[0]) for _ in range(len(it))]
  for i in range(len(it)):
    for j in range(len(it[i])):
      puz[j][i] = it[i][j]
  return puz



def getcage(cages, cell):
  for i in cages:
    for num in i[1:]:
      if num == cell:
        return i
        



def isvalidcage(puzzle, cage):
  ispart = False
  cells_sum = 0
  for cell in cage[1:]:
    row, col = getcoords(cell, len(puzzle))
    cells_sum += puzzle[row][col]
    if(puzzle[row][col] == 0):
      ispart = True
  if ispart:
    return (cells_sum < cage[0], False)
  else:
    return (cells_sum == cage[0], True)



def isvalidrowscols(puzzle):
  for row in puzzle:
    nodupes = [x for x in removedupes(row) if x != 0]
    row = [x for x in row if x != 0]
    if(len(nodupes) != len(row)):
      return False
  for col in transpose(puzzle):
    nodupes = [x for x in removedupes(col) if x != 0]
    col = [x for x in col if x != 0]
    if(len(nodupes) != len(col)):
      return False
  return True



def isvalidpuzzle(puzzle, cages):
  for cage in cages:
    valid, full = isvalidcage(puzzle, cage)
    if not (valid and full):
      return False
  if not isvalidrowscols(puzzle):
    return False
  return True



def solve(cages):
  #n = int((max(max(a[1:] for a in cages)) + 1) ** 0.5)
  zoop = []
  zoop = [max(a[1:]) for a in cages]
  n = 0
  n = int((max(zoop) + 1) ** 0.5)
  puzzle = [[0] * n for _ in range(n)]
  cnt = 0
  i = 0
  while not isvalidpuzzle(puzzle, cages):
    cnt += 1
    row, col = getcoords(i, n)
    curcage = getcage(cages, i)
    puzzle[row][col] += 1
    if(puzzle[row][col] > curcage[0]):
      puzzle[row][col] = 0
      i -= 1
    else:
      validcage, full = isvalidcage(puzzle, curcage)
      if (validcage and isvalidrowscols(puzzle)):
        i += 1
  return puzzle



def main() -> None:
  size = int(input())
  cages = [None] * size
  for i in range(size):
    res = []
    for num in input().rstrip().split():
      res.append(int(num))
    cages[i] = res
  printpuzzle(solve(cages))
