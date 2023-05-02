package org.example;

import java.util.*;

public class 등산코스_정하기 {
    private static List<List<Node>> graph;

    private static class Node {
        int e, w;

        Node(int e, int w) {
            this.e = e;
            this.w = w;
        }
    }

    public static int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        graph = new ArrayList<>();
        for (int i = 0; i < n + 1; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] path : paths) {
            int s = path[0];
            int e = path[1];
            int w = path[2];

            if (isGate(s, gates) || isSummit(e, summits)) {
                graph.get(s).add(new Node(e, w));
            } else if (isGate(e, gates) || isSummit(s, summits)) {
                graph.get(e).add(new Node(s, w));
            } else {
                graph.get(s).add(new Node(e, w));
                graph.get(e).add(new Node(s, w));
            }
        }

        return dijkstra(n, gates, summits);
    }

    private static int[] dijkstra(int n, int[] gates, int[] summits) {
        Queue<Node> qu = new LinkedList<>();
        int[] intensity = new int[n + 1];

        Arrays.fill(intensity, Integer.MAX_VALUE);

        for (int gate : gates) {
            qu.add(new Node(gate, 0));
            intensity[gate] = 0;
        }

        while (!qu.isEmpty()) {
            Node cn = qu.poll();

            if(cn.w > intensity[cn.e]) continue;

            for (int i = 0; i < graph.get(cn.e).size(); i++) {
                Node nn = graph.get(cn.e).get(i);

                int dis = Math.max(intensity[cn.e], nn.w);
                if (intensity[nn.e] > dis) {
                    intensity[nn.e] = dis;
                    qu.add(new Node(nn.e, dis));
                }
            }
        }

        int mn = Integer.MAX_VALUE;
        int mw = Integer.MAX_VALUE;

        Arrays.sort(summits);

        for (int summit : summits) {
            if (intensity[summit] < mw) {
                mn = summit;
                mw = intensity[summit];
            }
        }

        return new int[]{mn, mw};
    }

    private static boolean isGate(int num, int[] gates) {
        for (int gate : gates) {
            if (num == gate) return true;
        }
        return false;
    }

    private static boolean isSummit(int num, int[] submits) {
        for (int submit : submits) {
            if (num == submit) return true;
        }
        return false;
    }
}