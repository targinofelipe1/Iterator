package grafo.dirigido;


import java.util.*;

public class DFSIterator<T> implements IteratorGrafo<Vertice<T>> {

    private final List<Vertice<T>> vertices;
    private final Queue<Vertice<T>> fila;

    public DFSIterator(List<Vertice<T>> vertices, T carga) {
        this.vertices = vertices;
        this.fila = new LinkedList<>();
        this.DFS(carga);
    }

    @Override
    public boolean hasNext() {
        return !fila.isEmpty();
    }

    @Override
    public Vertice<T> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return fila.remove();
    }
    public Vertice<T> getVertice(T carga) {
        return vertices.stream()
                .filter(u -> u.getCarga().equals(carga))
                .findFirst()
                .orElse(null);
    }

    public void adicionarNaFila(Vertice<T> carga) {
        if (!fila.contains(carga)) {
            fila.add(carga);
        }
    }

    public void DFS(T source) {
        Vertice<T> u = getVertice(source);

        if (u == null) {
            System.err.println("Vértice não encontrado em dfs()");
            return;
        }

        vertices.forEach(vertex -> vertex.setStatus(VertexState.Unvisited));

        runDFS(u);
    }

    private void runDFS(Vertice<T> u) {
        List<Aresta<T>> uAdjacentes = u.getAdj();

        u.setStatus(VertexState.Visited);

        for (Aresta<T> arco : uAdjacentes) {
            Vertice<T> w = arco.getDestino();
            if (w.getStatus() == VertexState.Unvisited) {
                runDFS(w);
            }
        }
        adicionarNaFila(u);
        u.setStatus(VertexState.Finished);
    }
}
