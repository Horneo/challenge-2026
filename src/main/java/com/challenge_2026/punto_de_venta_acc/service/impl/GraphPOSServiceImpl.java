package com.challenge_2026.punto_de_venta_acc.service.impl;

import com.challenge_2026.punto_de_venta_acc.dto.GraphPOSDto;
import com.challenge_2026.punto_de_venta_acc.dto.NodoCosto;
import com.challenge_2026.punto_de_venta_acc.dto.ResultadoDijkstra;
import com.challenge_2026.punto_de_venta_acc.dto.RutaDetalladaResponse;
import com.challenge_2026.punto_de_venta_acc.mapper.ResponseGraphPOSMapper;
import com.challenge_2026.punto_de_venta_acc.entity.GraphPointOfSale;
import com.challenge_2026.punto_de_venta_acc.repository.GraphPOSRepository;
import com.challenge_2026.punto_de_venta_acc.service.GraphPOSService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GraphPOSServiceImpl implements GraphPOSService {
    private final GraphPOSRepository repo;

    private final POSServiceImpl posService;

    public GraphPOSServiceImpl(GraphPOSRepository repo, POSServiceImpl posService) {
        this.repo = repo;
        this.posService = posService;
    }

    @Cacheable( cacheNames = "graphPDVAll", key = "'all'")
    @Transactional(readOnly = true)
    public List<GraphPOSDto> findAll() {
        return repo.findAll().stream().map(ResponseGraphPOSMapper::toDto).toList();
    }

    @Transactional
    @CacheEvict( cacheNames = "graphPDVAll", key = "'all'")
    @CachePut( cacheNames = "graphPDVById", key = "#result.id")
    public GraphPointOfSale create(GraphPointOfSale pos) {
        return repo.save(pos);
    }


    @Cacheable( value = "rutas", key = "{#inicio, #fin}")
    public RutaDetalladaResponse calculateMinimumRoutesWithDijstra(String inicio, String fin) {

        List<GraphPointOfSale> routes = repo.findAll();

        Map<String, List<NodoCosto>> grafo = construirGrafoBidireccional(routes);

        if( !grafo.containsKey(inicio) || !grafo.containsKey(fin)) {
            throw new RuntimeException("El punto de venta de origen o destino no existen en la base de datos");
        }

        ResultadoDijkstra resultadoDijkstra = ejecutarDijkstra(grafo, inicio, fin);

        List<String> caminoIda = resultadoDijkstra.camino();
        List<String> caminoVuelta = new ArrayList<>(caminoIda);
        Collections.reverse(caminoVuelta);

        return new RutaDetalladaResponse(inicio, fin, caminoIda, caminoVuelta, resultadoDijkstra.costoTotal(), "Calculo bidireccional calculado con exito");

        }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict( cacheNames = "graphPDVAll", key = "'all'"),
                    @CacheEvict( cacheNames = "minimumRoutesAll", key = "'all'")
            }
    )
    public void delete(String pointA, String pointB) { repo.deleteByOriginPointOfSaleAndDestinationPointOfSale(pointA, pointB);}


    private Map<String, List<NodoCosto>> construirGrafoBidireccional(List<GraphPointOfSale> routes) {
        Map<String, List<NodoCosto>> grafo = new HashMap<>();
        for(GraphPointOfSale coneccion: routes) {
            grafo.computeIfAbsent(coneccion.getOriginPointOfSale(), k -> new ArrayList<>()).add( new NodoCosto(coneccion.getDestinationPointOfSale(), coneccion.getCost()));

            grafo.computeIfAbsent(coneccion.getDestinationPointOfSale(), k -> new ArrayList<>()).add(new NodoCosto(coneccion.getOriginPointOfSale(), coneccion.getCost()));
        }
        return grafo;
    }

    public ResultadoDijkstra ejecutarDijkstra(Map<String, List<NodoCosto>> grafo, String inicio, String fin) {
        Map<String , Double> distancias = new HashMap<>();
        Map<String, String> padres = new HashMap<>();
        PriorityQueue<NodoCosto> cola = new PriorityQueue<>(Comparator.comparingDouble(NodoCosto::costo));

        grafo.keySet().forEach( nodo -> distancias.put(nodo, Double.MAX_VALUE));
        distancias.put(inicio, 0.0);
        cola.add(new NodoCosto(inicio , 0.0));


        while(!cola.isEmpty()) {
            NodoCosto actual = cola.poll();

            if(actual.nombre().equals(fin)) break;

            for(NodoCosto vecino: grafo.getOrDefault(actual.nombre(), List.of())) {
                double nuevaDistancia = distancias.get(actual.nombre()) + vecino.costo();
                if( nuevaDistancia < distancias.get(vecino.nombre())) {
                    distancias.put(vecino.nombre(), nuevaDistancia);
                    padres.put(vecino.nombre(), actual.nombre());
                    cola.add( new NodoCosto(vecino.nombre(), nuevaDistancia));
                }
            }
        }
        LinkedList<String> camino = new LinkedList<>();
        String paso = fin;
        while(paso != null) {
            camino.addFirst(paso);
            paso = padres.get(paso);
        }
        return new ResultadoDijkstra(camino, distancias.get(fin));
    }

}

