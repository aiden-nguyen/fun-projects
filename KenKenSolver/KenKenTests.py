import KenKenSolver

assert KenKenSolver.getcage([[5, 0, 5], [8, 1, 2, 6]], 1) == [8, 1, 2, 6]
        assert KenKenSolver.getcage([[1, 2], [3, 2, 1]], 1) == [3, 2, 1]
        assert KenKenSolver.getcage([[5, 0, 5], [8, 1], [2, 6]], 5) == [5, 0, 5]
        assert KenKenSolver.getcage([[9, 0, 1, 2],
        [3, 3, 7],
        [7, 4, 8],
        [5, 5, 6],
        [6, 9, 10, 11],
        [4, 12, 13],
        [6, 14, 15]], 3) == [3, 3, 7]
        assert KenKenSolver.getcage([[9, 0, 1, 2],
        [3, 3, 7],
        [7, 4, 8],
        [5, 5, 6],
        [6, 9, 10, 11],
        [4, 12, 13],
        [6, 14, 15]], 18) == None

        assert KenKenSolver.getcoords(2, 33) == (0, 2)
        assert KenKenSolver.getcoords(4, 24) == (0, 4)
        assert KenKenSolver.getcoords(5, 29) == (0, 5)

        assert KenKenSolver.isvalidcage(
        [[4, 1, 2, 5, 3],
        [1, 5, 4, 3, 2],
        [2, 3, 5, 4, 1],
        [3, 4, 1, 2, 5],
        [5, 2, 3, 1, 4]], [5, 0, 5]) == (True, True)

        assert KenKenSolver.isvalidcage(
        [[1, 1, 2, 5, 3],
        [1, 5, 4, 3, 2],
        [2, 3, 5, 4, 1],
        [3, 4, 1, 2, 5],
        [5, 2, 3, 1, 4]], [5, 0, 5]) == (False, True)

        assert KenKenSolver.isvalidcage(
        [[4, 1, 2, 5, 3],
        [1, 5, 4, 3, 2],
        [2, 3, 5, 4, 1],
        [3, 4, 1, 2, 5],
        [5, 2, 3, 1, 4]], [10, 19, 23, 24]) == (True, True)

        assert KenKenSolver.isvalidpuzzle(
        [[5, 1, 2, 5, 3],
        [1, 5, 4, 3, 2],
        [2, 3, 5, 4, 1],
        [3, 4, 1, 2, 5],
        [5, 2, 3, 1, 4]], [[5, 0, 5], [8, 1, 2, 6]]) == False
        assert KenKenSolver.isvalidpuzzle(
        [[4, 1, 2, 5, 3],
        [1, 5, 4, 3, 2],
        [2, 3, 5, 4, 1],
        [3, 4, 1, 2, 5],
        [5, 2, 3, 1, 4]], [[5, 0, 5], [8, 3, 8]]) == True
        assert KenKenSolver.isvalidpuzzle(
        [[1, 2],
        [1, 2]], [[3, 0, 1], [3, 2, 3]]) == False
        assert KenKenSolver.isvalidrowscols(
        [[1, 0, 3],
        [4, 0, 5],
        [0, 2, 0]]
        ) == True
        #rows
        assert KenKenSolver.isvalidrowscols(
        [[1, 1, 3],
        [4, 0, 5],
        [0, 2, 0]]
        ) == False
        #cols
        assert KenKenSolver.isvalidrowscols(
        [[1, 0, 3],
        [4, 0, 5],
        [4, 2, 0]]
        ) == False
        assert KenKenSolver.isvalidpuzzle(
        [[1, 2],
        [2, 5]], [[3, 0, 2], [7, 1, 3]]) == True

        assert KenKenSolver.removedupes(
        [0, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4]
        ) == [0, 1, 2, 3, 4]

        assert KenKenSolver.removedupes(
        [1, 2, 3, 4]
        ) == [1, 2, 3, 4]

        assert KenKenSolver.removedupes(
        [1, 1, 1, 1]
        ) == [1]

        assert KenKenSolver.transpose(
        [[1, 2, 3],
        [3, 2, 1],
        [2, 3, 1]]) == [[1, 3, 2], [2, 2, 3], [3, 1, 1]]
        assert KenKenSolver.transpose(
        [[1, 2],
        [5, 6]]) == [[1, 5], [2, 6]]
        assert KenKenSolver.transpose(
        [[1, 2, 3, 4],
        [5, 6, 7, 8],
        [6, 5, 4, 3],
        [8, 3, 2, 1]])  == [[1, 5, 6, 8], [2, 6, 5, 3], [3, 7, 4, 2], [4, 8, 3, 1]]

        assert KenKenSolver.solve(
        [[9, 0, 1, 2],
        [3, 3, 7],
        [7, 4, 8],
        [5, 5, 6],
        [6, 9, 10, 11],
        [4, 12, 13],
        [6, 14, 15]]
        ) == [[2, 4, 3, 1], [3, 1, 4, 2], [4, 2, 1, 3], [1, 3, 2, 4]]

        assert KenKenSolver.solve(
        [[5, 0, 1],
        [3, 2, 5],
        [4, 3, 6],
        [1, 4],
        [5, 7, 8]]) == [[2, 3, 1], [3, 1, 2], [1, 2, 3]]

        assert KenKenSolver.solve(
        [[5, 0, 5],
        [8, 1, 2, 6],
        [8, 3, 8],
        [6, 4, 9, 14],
        [13, 7, 12, 13],
        [5, 10, 15],
        [14, 11, 16, 20, 21],
        [6, 17, 18, 22],
        [10, 19, 23, 24]]) == [[4, 1, 2, 5, 3],
        [1, 5, 4, 3, 2],
        [2, 3, 5, 4, 1],
        [3, 4, 1, 2, 5],
        [5, 2, 3, 1, 4]]
