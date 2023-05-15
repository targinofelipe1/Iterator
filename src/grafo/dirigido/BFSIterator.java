package grafo.dirigido;

import java.util.*;

public class BFSIterator<T> implements IteratorGrafo<Vertice<T>> {

    private final List<Vertice<T>> vertices;
    private final List<Aresta<T>> arestas;
    private final Queue<Vertice<T>> fila;

    public BFSIterator(List<Vertice<T>> vertices, List<Aresta<T>> arestas, T carga) {
        this.vertices = vertices;
        this.arestas = arestas;
        this.fila = new LinkedList<>();
        this.BFS(carga);
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

    private Vertice<T> getVertice(T carga) {
        return vertices.stream()
                .filter(v -> v.getCarga().equals(carga))
                .findFirst()
                .orElse(null);
    }

    private boolean exists(Vertice<T> v) {
        return vertices.contains(v);
    }

    private List<Vertice<T>> incidentes(Vertice<T> u) {
        List<Vertice<T>> vertex = new ArrayList<>();

        for (Aresta<T> arco : arestas) {
            if (arco.getDestino().equals(u))
                vertex.add(arco.getOrigem());
            else if (arco.getOrigem().equals(u))
                vertex.add(arco.getDestino());
        }
        return vertex;
    }

    private void adicionarNaFila(Vertice<T> carga) {
        if (!fila.contains(carga)) {
            fila.add(carga);
        }
    }

    private void BFS(T source) {
        Queue<Vertice<T>> q = new LinkedList<>();
        List<Vertice<T>> uAdjacentes;

        Vertice<T> v = getVertice(source);

        if (!exists(v))
            return;

        vertices.forEach(vertex -> vertex.setStatus(VertexState.Unvisited));

        v.setStatus(VertexState.Visited);
        q.add(v);

        while (!q.isEmpty()) {
            Vertice<T> u = q.remove();
            uAdjacentes = incidentes(u);

            for (Vertice<T> w : uAdjacentes) {
                if (w.getStatus() == VertexState.Unvisited) {
                    w.setStatus(VertexState.Visited);
                    q.add(w);
                }
            }
            adicionarNaFila(u);
            u.setStatus(VertexState.Finished);
        }
    }
}