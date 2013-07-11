(ns pathfinder.core)


(defn neighbors
  ([size xy] (neighbors [[-1 0] [1 0] [0 -1] [0 1]] size xy))
  ([deltas size xy]
     (filter (fn [new-xy]
               (every? #(< -1 % size) new-xy))
             (map #(map + xy %) deltas))))

(defn estimate-cost
  [step-cost-est size x y]
  (* step-cost-est
     (- (+ size size) x y 2)))

(defn path-cost
  [node-cost cheapest-nbr]
  (+ node-cost
     (:cost cheapest-nbr 0)))

(defn total-cost
  [new-cost step-cost-est size x y]
  (+ new-cost
     (estimate-cost step-cost-est size x y)))
