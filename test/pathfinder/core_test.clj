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
