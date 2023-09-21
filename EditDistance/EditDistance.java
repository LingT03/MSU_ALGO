package EditDistance;

public class EditDistance {
    public int distanc(String a, String b) {
        int alen = a.length();
        int blen = b.length();

        int[][] dist = new int[alen + 1][blen + 1];

        for (int i = 0; i < alen + 1; i++) {
            dist[i][0] = i;
        }
        for (int j = 0; j < blen + 1; j++) {
            dist[0][j] = j;
        }

        for (int i = 1; i < blen + 1; i++) {
            for (int j = 1; j < alen + 1; j++) {
                if (a.substring(j - 1, j) == b.substring(i - 1, i)) {
                    dist[i][j] = dist[i - 1][j - 1];
                } else {
                    dist[i][j] = 1 + Math.min(dist[i - 1][j - 1], Math.min(dist[i - 1][j], dist[i][j - 1]));
                }
            }
        }
        return dist[alen][blen];
    }
}
