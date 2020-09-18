package tutorial;

import java.nio.file.Paths;

import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;

public class CargarModelo {
	
	static String FILE_NAME = "./modelos/example-IoT-fm.xml";

	public static void main(String[] args) {
		
		System.out.println("Modelo de Features : " +  FILE_NAME);
	
		// Carga el modelo
		IFeatureModel fm = null;
		fm = FeatureModelManager.load(Paths.get(FILE_NAME)).getObject();		
		
		if (fm == null) {
			System.out.println("Error cargando del modelo");
			System.exit(0);
		}		
		
		// Imprime información del modelo
		System.out.println();
		System.out.println("Archivo             : " + fm.getSourceFile().toString());
		System.out.println("Num Características : " + fm.getNumberOfFeatures());
		
	}
	
}
