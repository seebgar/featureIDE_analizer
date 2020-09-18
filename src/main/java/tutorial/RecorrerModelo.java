package tutorial;

import java.nio.file.Paths;
import java.util.List;

import de.ovgu.featureide.fm.core.base.IConstraint;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.IFeatureStructure;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;

public class RecorrerModelo {

	static String FILE_NAME = "./modelos/example-IoT-fm.xml";

	/**
	 * Método principal. Recorre los elementos de un modelo de características,
	 * imprimiendo información de cada una de las características.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Modelo de Features : " + FILE_NAME);

		// Carga el modelo
		IFeatureModel fm = null;
		fm = FeatureModelManager.load(Paths.get(FILE_NAME)).getObject();

		// fm es null si no se pudo cargar el modelo
		if (fm == null) {
			System.err.println("Error cargando del modelo");
			System.exit(0);
		}

		// Obtiene la característica raíz
		IFeatureStructure root = fm.getStructure().getRoot();

		// Muestra la información del árbol
		System.out.println("Árbol de características");
		muestraArbol(root);

		// Muestra la información de las restricciones adicionales
		System.out.println("Restricciones adicionales");
		muestraRestricciones(fm.getConstraints());	
	}
	
	// == árbol de características

	/**
	 * Muestra los elementos de un modelo (o un de sub-árbol) de features. Realiza
	 * un recorrido del árbol en pre-orden.
	 * 
	 * @param f		caracteristica padre
	 */
	public static void muestraArbol(IFeatureStructure f) {
		muestraArbol(f, 1);
	}

	/**
	 * Muestra los elementos de un modelo (o un de sub-árbol) de features. Realiza
	 * un recorrido del árbol en pre-orden.
	 * 
	 * @param f      caracteristica padre
	 * @param nivel  nivel de la característica, para indentar el nombre
	 */
	public static void muestraArbol(IFeatureStructure f, int nivel) {

		// Determina la cantidad de "tabs" a mostrar de acuerdo al nivel
		// En Java 11: "\t".repeat(nivel)
		// En Java 7+: String.join("", Collections.nCopies(nivel, "\t"));
		String tabs = "";
		for (int i = 0; i < nivel; i++) {
			tabs += "\t";
		}

		// Muestra información de la característica
		System.out.println(
				tabs 
				+ f.getFeature().getName() 
				+ " | " + (f.isMandatory() ? "Obligatorio" : "Opcional")
				+ " | " + (f.isAbstract() ? "Abstract" : "") 
					+ (f.isAnd() ? "Grupo normal" : "")
					+ (f.isAlternative() ? "Grupo Alternativo" : "") 
					+ (f.isOr() ? "Grupo Or" : ""));

		// Imprime la información de los hijos, si los tiene
		if (f.hasChildren()) {
			for (IFeatureStructure fs : f.getChildren()) {
				muestraArbol(fs, nivel + 1);
			}
		}
	}

	// == Restricciones
	
	/**
	 * Muestra un conjunto de restricciones
	 * @param constraints	listado de restricciones
	 */
	public static void muestraRestricciones(List<IConstraint> constraints) {
		for (IConstraint cs : constraints) {
			System.out.println("\t" + cs.getDisplayName());
		}
	}

}
