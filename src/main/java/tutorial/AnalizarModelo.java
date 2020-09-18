package tutorial;

import java.nio.file.Paths;

import de.ovgu.featureide.fm.core.FeatureModelAnalyzer;
import de.ovgu.featureide.fm.core.base.FeatureUtils;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;

public class AnalizarModelo {

	static String FILE_NAME = "./modelos/example-IoT-fm.xml";
	
	/**ation 
	 * Método principal. 
	 * Recorre los elementos de un modelo de características, imprimiendo información de cada una de las características.
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Modelo de Features : " +  FILE_NAME);
		
		// Carga el modelo
		IFeatureModel fm = null;
		fm = FeatureModelManager.load(Paths.get(FILE_NAME)).getObject();
				
		if (fm == null) {
			System.err.println("Error cargando del modelo");
			System.exit(0);
		}
		
		// obtiene un analizador de modelos
		FeatureModelAnalyzer analyzer = FeatureUtils.getAnalyser(fm);

		// Es un módelo válido
		try {
			System.out.println("Válido : " +  analyzer.isValid());
		} catch (Exception e) {
			System.err.println("Error analizando el modelo");
			System.exit(0);
		}
		
		// Características core
		System.out.println("Core Features");
		for(IFeature caracteristica: analyzer.getCoreFeatures()) {
			System.out.println("\t" + caracteristica.getName());
		}
		
		// Características muertas
		System.out.println("Dead Features");
		for(IFeature caracteristica: analyzer.getDeadFeatures()) {
			System.out.println("\t" + caracteristica.getName());
		}
	
		// Características falsa opcionales
		System.out.println("False Optional");
		for(IFeature caracteristica: analyzer.getFalseOptionalFeatures()) {
			System.out.println("\t" + caracteristica.getName());
		}
	
		// determina la cantidad de configuraciones
		Configuration configs = new Configuration(fm);		
		long cantConfiguraciones = configs.number(20000, true);
		System.out.println("Num de Configuraciones : " + cantConfiguraciones);		
		
	}

	
}
