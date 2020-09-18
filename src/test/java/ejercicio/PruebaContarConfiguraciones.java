package ejercicio;

import org.junit.Test;

import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.IFeatureStructure;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;

import static org.junit.Assert.*;

import java.nio.file.Paths;

public class PruebaContarConfiguraciones {

	private IFeatureStructure cargarArbol(String nombreArchivo) {

		IFeatureModel fm = null;
		fm = FeatureModelManager.load(Paths.get(nombreArchivo)).getObject();
					
		if (fm == null) {
			throw new RuntimeException("No se pudo cargar " + nombreArchivo);
		}

		return fm.getStructure().getRoot();
	}
	
	@Test
	public void probarExampleIot() {

		IFeatureStructure arbol = cargarArbol("./modelos/example-IoT-fm.xml");
		assertEquals("example-IoT", 42, ContarConfiguraciones.contarConfiguraciones(arbol));

		arbol = cargarArbol("./modelos/example-IoT-fm-sin-restricciones.xml");
		assertEquals("example-IoT", 42, ContarConfiguraciones.contarConfiguraciones(arbol));
		
	}
		
}
