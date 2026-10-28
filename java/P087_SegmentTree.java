public class P087_SegmentTree {
    static class SegTree {
        final int n;
        final long[] sum, mn;
        SegTree(int[] a) {
            n = a.length;
            sum = new long[4 * n];
            mn = new long[4 * n];
            build(1, 0, n - 1, a);
        }
        void build(int node, int l, int r, int[] a) {
            if (l == r) { sum[node] = mn[node] = a[l]; return; }
            int m = (l + r) / 2;
            build(2 * node, l, m, a);
            build(2 * node + 1, m + 1, r, a);
            pull(node);
        }
        void pull(int node) {
            sum[node] = sum[2 * node] + sum[2 * node + 1];
            mn[node] = Math.min(mn[2 * node], mn[2 * node + 1]);
        }
        void update(int node, int l, int r, int idx, int val) {
            if (l == r) { sum[node] = mn[node] = val; return; }
            int m = (l + r) / 2;
            if (idx <= m) update(2 * node, l, m, idx, val); else update(2 * node + 1, m + 1, r, idx, val);
            pull(node);
        }
        long[] query(int node, int l, int r, int ql, int qr) {
            if (qr < l || r < ql) return new long[]{0, Long.MAX_VALUE};
            if (ql <= l && r <= qr) return new long[]{sum[node], mn[node]};
            int m = (l + r) / 2;
            long[] a = query(2 * node, l, m, ql, qr), b = query(2 * node + 1, m + 1, r, ql, qr);
            return new long[]{a[0] + b[0], Math.min(a[1], b[1])};
        }
        void set(int i, int v) { update(1, 0, n - 1, i, v); }
        String range(int l, int r) { long[] q = query(1, 0, n - 1, l, r); return "sum=" + q[0] + " min=" + q[1]; }
    }

    static class Fenwick {
        final long[] t;
        Fenwick(int n) { t = new long[n + 1]; }
        void add(int i, long d) { for (i++; i < t.length; i += i & -i) t[i] += d; }
        long prefix(int i) { long s = 0; for (i++; i > 0; i -= i & -i) s += t[i]; return s; }
    }

    public static void main(String[] args) {
        int[] a = {5, 2, 8, 6, 3, 7, 1, 4};
        SegTree st = new SegTree(a);
        System.out.println(st.range(0, 7) + " | " + st.range(2, 5) + " | " + st.range(6, 6));
        st.set(6, 10);
        st.set(1, -3);
        System.out.println(st.range(0, 7) + " | " + st.range(0, 3) + " | " + st.range(5, 7));
        Fenwick f = new Fenwick(a.length);
        for (int i = 0; i < a.length; i++) f.add(i, a[i]);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) sb.append(f.prefix(i)).append(' ');
        System.out.println(sb.toString().trim());
        f.add(3, 100);
        System.out.println(f.prefix(7) - f.prefix(2));
    }
}
