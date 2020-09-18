package ejercicio;

import java.nio.file.Paths;

import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.IFeatureStructure;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;

public class ContarConfiguraciones {

	static String FILE_NAME = "./modelos/example-IoT-fm-sin-restricciones.xml";

	public static void main(String[] args) {

		System.out.println("Modelo de Features : " + FILE_NAME);

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
	 * @param f característica padre del modelo
	 * @return número de configuraciones validas
	 */
	public static long contarConfiguraciones(IFeatureStructure f) {

		/*
		 * El algoritmo implementa las siguientes reglas
		 * 
		 * count root(c) = count(c) count mandatory(c) = count(c) count optional(c) =
		 * count(c) + 1 count and(c1, . . . , cn) = count(c1) * . . . * count(cn) count
		 * alternative(c1, . . . , cn) = count(c1) + . . . + count(cn) count or(c1, . .
		 * . , cn) = (count(c1) + 1) * . . . * (count(cn) + 1) - 1 count leaf = 1
		 */

		long count = 0;

		// si la característica es una hoja
		// regla: count leaf = 1
		if (!f.hasChildren()) {
			count = 1;

			// si la característica es un grupo AND
			// regla: count and(c1, . . . , cn) = count(c1) * ... * count(cn)
		} else if (f.isAnd()) {
			count = 1;
			for (IFeatureStructure fs : f.getChildren()) {
				count *= contarConfiguraciones(fs);
			}

			// si la característica es un grupo alternativo
			// regla: count alternative(c1, . . . , cn) = count(c1) + ... + count(cn)
		} else if (f.isAlternative()) {
			for (IFeatureStructure fs : f.getChildren()) {
				count += contarConfiguraciones(fs);
			}

			// si la característica es un grupo or
			// regla: count or(c1, . . . , cn) = (count(c1) + 1) * ... * (count(cn) + 1) - 1
		} else if (f.isOr()) {
			count = 1;
			for (IFeatureStructure fs : f.getChildren()) {
				count *= (contarConfiguraciones(fs) + 1);
			}
			count--;
		}
		
		// si la característica es opcional
        // regla: count optional(c) = count(c) + 1
        if (!f.isMandatory()) {
                count++;
        }
                        
        // si la característica es obligatoria, no se requiere hacer nada
        // count mandatory(c)  = count(c)

		return count;
	}

}
