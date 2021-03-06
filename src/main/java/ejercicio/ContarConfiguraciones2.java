package ejercicio;

import java.math.BigInteger;
import java.nio.file.Paths;

import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.IFeatureStructure;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;

public class ContarConfiguraciones2 {

	static String FILE_NAME = "./modelos/linux-2.6.33.3-model.xml";

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

		// determina la cantidad de configuraciones usando el método de conteo
		System.out.println("Usando el algoritmo : " + contarConfiguraciones(fm.getStructure().getRoot()));
	}

	/**
	 * Counts the valid configurations in a feature model without textual or
	 * cross-tree constraints
	 * 
	 * @param f
	 *            parent feature of the tree to count the valid configurations
	 * @return number of valid configurations in the subtree
	 */
	public static BigInteger contarConfiguraciones(IFeatureStructure f) {

		/*
		 * El algoritmo implementa las siguientes reglas
		 * 
		 * count root(c) = count(c) count mandatory(c) = count(c) count optional(c) =
		 * count(c) + 1 count and(c1, . . . , cn) = count(c1) * . . . * count(cn) count
		 * alternative(c1, . . . , cn) = count(c1) + . . . + count(cn) count or(c1, . .
		 * . , cn) = (count(c1) + 1) * . . . * (count(cn) + 1) - 1 count leaf = 1
		 */

		BigInteger count = BigInteger.ZERO;

		// si la característica es una hoja
		// regla: count leaf = 1
		if (!f.hasChildren()) {
			count = BigInteger.ONE;

			// si la característica es un grupo AND
			// regla: count and(c1, . . . , cn) = count(c1) * ... * count(cn)
		} else if (f.isAnd()) {
			count = BigInteger.ONE;
			for (IFeatureStructure fs : f.getChildren()) {
				count = count.multiply( contarConfiguraciones(fs));
			}

			// si la característica es un grupo alternativo
			// regla: count alternative(c1, . . . , cn) = count(c1) + ... + count(cn)
		} else if (f.isAlternative()) {
			for (IFeatureStructure fs : f.getChildren()) {
				count = count.add(contarConfiguraciones(fs));
			}

			// si la característica es un grupo or
			// regla: count or(c1, . . . , cn) = (count(c1) + 1) * ... * (count(cn) + 1) - 1
		} else if (f.isOr()) {
			count = BigInteger.ONE;
			for (IFeatureStructure fs : f.getChildren()) {
				  count = count.multiply(contarConfiguraciones(fs)
                          .add(BigInteger.ONE));
			}
			 count = count.subtract(BigInteger.ONE);
		}
		
		// si la característica es opcional
        // regla: count optional(c) = count(c) + 1
        if (!f.isMandatory()) {
        	 count = count.add(BigInteger.ONE);
        }
                        
        // si la característica es obligatoria, no se requiere hacer nada
        // count mandatory(c)  = count(c)

		return count;
		
	}

}
