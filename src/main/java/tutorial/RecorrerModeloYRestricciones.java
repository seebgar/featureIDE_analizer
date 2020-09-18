package tutorial;

import java.nio.file.Paths;
import java.util.List;

import org.prop4j.And;
import org.prop4j.Implies;
import org.prop4j.Literal;
import org.prop4j.Node;
import org.prop4j.Not;
import org.prop4j.Or;

import de.ovgu.featureide.fm.core.base.IConstraint;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.IFeatureStructure;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;

public class RecorrerModeloYRestricciones {

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

		// Mostrando la información del árbol
		System.out.println("Árbol de características");
		muestraArbol(root);

		// Mostrando las restricciones adicionales
		System.out.println("Restricciones adicionales");
		muestraRestricciones(fm.getConstraints());
	}
	
	// == árbol de características

	/**
	 * Muestra los elementos de un modelo (o un de sub-árbol) de features. Realiza
	 * un recorrido del árbol en pre-orden.
	 * 
	 * @param f
	 *            caracteristica padre
	 */
	public static void muestraArbol(IFeatureStructure f) {
		muestraArbol(f, 1);
	}

	/**
	 * Muestra los elementos de un modelo (o un de sub-árbol) de features. Realiza
	 * un recorrido del árbol en pre-orden.
	 * 
	 * @param f
	 *            caracteristica padre
	 * @param nivel
	 *            nivel de la característica, para indentar el nombre
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
		System.out.println(tabs + f.getFeature().getName() + " | " + (f.isMandatory() ? "Obligatorio" : "Opcional")
				+ " | " + (f.isAbstract() ? "Abstract" : "") + (f.isAnd() ? "Grupo normal" : "")
				+ (f.isAlternative() ? "Grupo Alternativo" : "") + (f.isOr() ? "Grupo Or" : ""));

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
			muestraArbolRestriccion(cs, 2);
		}
	}
	
	/**
	 * Muestra el árbol de una restricción, mostrando los operadores (AND, OR, IMPLIES, NOT, ...) y los 
	 * literares (nombres de características)
	 * 
	 * @param constraint	restricciones
	 * @param nivel			nivel usado para indentar el contenido
	 */
	public static void muestraArbolRestriccion(IConstraint constraint, int nivel) {
		Node propNode = constraint.getNode();
		muestraArbolRestriccion(propNode, 1);
	}

	/**
	 * Muestra el árbol de una restricción, mostrando los operadores (AND, OR, IMPLIES, NOT, ...) y los 
	 * literares (nombres de características)
	 * 
	 * @param propNode		nodo del árbol de la restricción
	 * @param nivel			nivel usado para indentar el contenido
	 */
	public static void muestraArbolRestriccion(Node propNode, int nivel) {

		// Determina la cantidad de "tabs" a mostrar de acuerdo al nivel
		// En Java 11: "\t".repeat(nivel)
		// En Java 7+: String.join("", Collections.nCopies(nivel, "\t"));
		String tabs = "";
		for (int i = 0; i < nivel; i++) {
			tabs += "\t";
		}

		// muestra la información de acuerdo al tipo de nodo
		System.out.println(
				tabs 
				// operadores
				+ ((propNode instanceof Implies) ? "implies" : "") 
				+ ((propNode instanceof And) ? "and" : "")
				+ ((propNode instanceof Not) ? "not" : "") 
				+ ((propNode instanceof Or) ? "or" : "")
				// nombre del literal
				+ ((propNode instanceof Literal) ? ((Literal) propNode).var.toString() : ""));

		// muestra la información de los nodos hijos
		if (propNode.getChildren() != null) {
			for (Node hijo : propNode.getChildren()) {
				muestraArbolRestriccion(hijo, nivel + 1);
			}
		}
	}

}
