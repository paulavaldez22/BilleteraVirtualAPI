package ar.com.ada.api.billeteravirtual.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.billeteravirtual.entities.*;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    Persona findByNombre(String nombrePersona);

    Persona findByDni(String dni);

    //Este caso aplica al named query creado en el objeto persona, fijarse que el nombre del named query coincide con el nombre de este metodo
    // y ademas posee un parametro
    List<Persona> findAllByNombreContiene(String nombre);

    //Igual al caso anterior pero con 2 parametros.
    List<Persona> findAllByNombreAndDNI(String nombre, String dni);

    //Este caso es diferente y es el que recomiendo, se pone el @Query arriba del metodo que queremos "crear" por interface
    //En este caso hace un select sobre las personas ordenada por nombre
    //recordar que esta sintaxis es JPQL
    @Query("select p from Persona p order by nombre")
    List<Persona> findAllOrderByNombre();

    //Mismo caso anterior, salvo que en este se le pone "nombre" al paremtro. En nuestro caso el nombre del parametro es ":dni"
    @Query("SELECT p FROM Persona p WHERE p.dni = :dni")
    List<Persona> findByDNIVersion2(@Param("dni") String descripcion);
}