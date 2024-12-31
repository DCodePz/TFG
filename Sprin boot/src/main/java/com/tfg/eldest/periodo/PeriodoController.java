package com.tfg.eldest.periodo;

import com.tfg.eldest.actividadformacion.ActividadFormacion;
import com.tfg.eldest.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: Restricciones de permisos

@RestController
@RequestMapping(path = "api/v1/periodos")
public class PeriodoController {
    private final PeriodoService periodoService;

    @Autowired
    public PeriodoController(PeriodoService periodoService) {
        this.periodoService = periodoService;
    }

    // GET
    @GetMapping
    public List<Periodo> getPeriodos() {
        return periodoService.getPeriodos();
    }

    @GetMapping(path = "{periodoId}")
    public Periodo getPeriodo(@PathVariable Long periodoId) {
        return periodoService.getPeriodo(periodoId);
    }

    @GetMapping(path = "buscar/{infoBuscar}")
    public List<Periodo> getPeriodosBusqueda(@PathVariable String infoBuscar) {
        return periodoService.getPeriodosBusqueda(infoBuscar);
    }

    // POST
    @PostMapping(path = "crear")
    public void crearPeriodo(@RequestBody Periodo periodo) {
        periodoService.crearPeriodo(periodo);
    }

    // PUT
    @PutMapping(path = "invertirHabilitado/{periodoId}")
    public void invertirHabilitado(@PathVariable Long periodoId) {
        periodoService.invertirHabilitado(periodoId);
    }

    @PutMapping(path = "guardar/{periodoId}")
    public void guardarPeriodo(@PathVariable Long periodoId,
                                 @RequestBody(required = false) Periodo periodoModificado) {
        periodoService.guardarPeriodo(periodoId, periodoModificado);
    }
}
