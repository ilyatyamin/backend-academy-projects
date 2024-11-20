package backend.academy.generators.containers;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация структуры данных UnionFind для быстрой работы алгоритмы Краскала
 * Реализовывал на другом курсе по АиСД.
 * Также можно прочитать здесь: <a href="https://dev.to/tamerlang/data-structures-union-find-4n1m">ссылка</a>
 */
public class UnionFind {
    private final List<Integer> parent;
    private final List<Integer> ranks;

    public UnionFind(int size) {
        parent = new ArrayList<>();

        ranks = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            ranks.add(0);
            parent.add(i);
        }
    }

    public int find(int x) {
        if (parent.get(x) != x) {
            parent.set(x,
                find(parent.get(x)));
        }
        return parent.get(x);
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            if (ranks.get(rootX) > ranks.get(rootY)) {
                parent.set(rootY, rootX);
            } else if (ranks.get(rootX) < ranks.get(rootY)) {
                parent.set(rootX, rootY);
            } else {
                parent.set(rootY, rootX);
                ranks.set(rootX, ranks.get(rootX) + 1);
            }
        }
    }
}
