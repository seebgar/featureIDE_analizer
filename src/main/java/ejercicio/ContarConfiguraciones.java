package ejercicio;

import java.nio.file.Paths;

import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.IFeatureStructure;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;

public class ContarConfiguraciones {

	static String FILE_NAME = "./modelos/example-IoT-fm-sin-restricciones.xml";
	
	public static void main(String[] args) {
	
		System.out.println("Modelo de Features : " +  FILE_NAME);
		
		// Carga el modelo
		IFeatureModel fm = null;
		fm = FeatureModelManager.load(Paths.get(FILE_NAME)).getObject();
				
		if (fm == null) {
			System.err.println("Error cargando del modelo");
			System.exit(0);
		}

		System.out.println("Número de Configuraciones");

		// determina la cantidad de configuraciones con una enumeración
		Configuration configs = new Configuration(fm);		
		long cantConfiguraciones = configs.number(1000, true);
		System.out.println("Usando FeatureIDE   : " + cantConfiguraciones);		
		
		// usando el método de conteo
		System.out.println("Usando el algoritmo : " + contarConfiguraciones(fm.getStructure().getRoot()));
	}
	
	
	
	/**
	 * Cuenta el número de configuraciones válidas de un modelo de características
	 * 
	 * @param 	f 	característica padre del modelo
	 * @return  número de configuraciones validas
	 */
	public static long contarConfiguraciones(IFeatureStructure f) {

		/*
			El algoritmo implementa las siguientes reglas
			
			count root(c)  = count(c)
			count mandatory(c)  = count(c)
		    count optional(c) = count(c) + 1
		    count and(c1, . . . , cn)  = count(c1) * . . . * count(cn)
		    count alternative(c1, . . . , cn)  = count(c1) + . . . + count(cn)
		    count or(c1, . . . , cn)  = (count(c1) + 1) * . . . * (count(cn) + 1) - 1
		    count leaf  = 1
		*/
		
		return 0;		
	}
	
	
}
