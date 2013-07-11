(ns pathfinder.core-test
  (:require [midje.sweet :refer :all]
            [pathfinder.core :refer :all]))


(def world [[1 1 1 1 1]
            [999 999 999 999 1]
            [1 1 1 1 1]
            [1 999 999 999 999]
            [1 1 1 1 1]])


(defn neighbors
  ([size xy] (neighbors [[-1 0] [1 0] [0 -1] [0 1]] size xy))
  ([deltas size xy]
     (filter (fn [new-xy]
               (every? #(< -1 % size) new-xy))
             (map #(map + xy %) deltas))))


(fact (neighbors 5 [0 0]) => (just [[1 0] [0 1]]))

(fact (neighbors 5 [4 4]) => (just [[3 4] [4 3]]))
