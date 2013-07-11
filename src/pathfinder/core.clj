(ns pathfinder.core)





(defn neighbors
  ([size xy] (neighbors [[-1 0] [1 0] [0 -1] [0 1]] size xy))
  ([deltas size xy]
     (filter (fn [new-xy]
               (every? #(< -1 % size) new-xy))
             (map #(map + xy %) deltas))))
