(ns pathfinder.core-test
  (:require [midje.sweet :refer :all]
            [pathfinder.core :refer :all]))


(def world [[1 1 1 1 1]
            [999 999 999 999 1]
            [1 1 1 1 1]
            [1 999 999 999 999]
            [1 1 1 1 1]])


(fact (neighbors 5 [0 0]) => (just [[1 0] [0 1]]))

(fact (neighbors 5 [4 4]) => (just [[3 4] [4 3]]))

(fact (estimate-cost 900 5 0 0) => 7200)

(fact (estimate-cost 900 5 4 4) => 0)

(fact (path-cost 900 {:cost 1}) => 901)

(fact (total-cost 0 900 5 0 0) => 7200)

(fact (total-cost 1000 900 5 3 4) => 1900)

(fact (min-by :cost [{:cost 100} {:cost 36} {:cost 9}]) => {:cost 9})

(fact (min-by :cost []) => nil)

(fact (vec (repeat 5 (vec (repeat 5 nil)))) => [[nil nil nil nil nil]
                                          [nil nil nil nil nil]
                                          [nil nil nil nil nil]
                                          [nil nil nil nil nil]
                                          [nil nil nil nil nil]])

(fact "PathFinder A* with world Z"
  (A* [0 0] 900 world) => [{:cost 17
                            :yxs [[0 0] [0 1] [0 2] [0 3] [0 4]
                                  [1 4] [2 4] [2 3] [2 2] [2 1] [2 0]
                                  [3 0] [4 0] [4 1] [4 2] [4 3] [4 4]]}
                           :steps 94])

(def shrubbery-world [[1 1 1 2 1]
                [1 1 1 999 1]
                [1 1 1 999 1]
                [1 1 1 999 1]
                [1 1 1 1 1]])


(fact "PathFinder A* with shrubbery world"
  (A* [0 0] 900 shrubbery-world) =>[{:cost 9,
                               :yxs [[0 0] [0 1] [0 2] [1 2] [2 2] [3 2]
                                     [4 2] [4 3] [4 4]]}
                              :steps 134])

(def bunny-world [[1 1 1 2 1]
                [1 1 1 999 1]
                [1 1 1 999 1]
                [1 1 1 999 1]
                [1 1 1 666 1]])

(fact "PathFinder A* with shrubbery world"
  (A* [0 0] 900 bunny-world) =>[{:cost 10,
                               :yxs [[0 0] [0 1] [0 2] [0 3] [0 4] [1 4]
                                     [2 4] [3 4] [4 4]]}
                              :steps 132])
