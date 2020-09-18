package ejercicio;

import org.junit.Test;

import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.IFeatureStructure;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;

import static org.junit.Assert.*;

import java.nio.file.Paths;

public class PruebaContarConfiguraciones2 {

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
		assertEquals("example-IoT", 
				"42", 
				ContarConfiguraciones2.contarConfiguraciones(arbol).toString());

		arbol = cargarArbol("./modelos/example-IoT-fm-sin-restricciones.xml");
		assertEquals("example-IoT", 
				"42", 
				ContarConfiguraciones2.contarConfiguraciones(arbol).toString());
		
	}

	@Test
	public void probarLinux() {

		IFeatureStructure arbol = cargarArbol("./modelos/linux-2.6.33.3-model.xml");
		assertEquals("linux-2.6.33.3-model.xml", 
				"39018260355522571304627724565202512348242708368982369641570682181289774821174806253682950367835404418242577381121079382782570364757577257282452870514865763949750365658399650607973264518734117189763618489335879149766657262685051444164710557196835915083304298365828990526973859762333378506749047043737338217966696848927768485492508196961304220478081366298510865854774755248519718396988619803402034168560765241319724477338626072332540340101369958042716789339032655426912944184773585962269850153407895888844827019714410566840195064034230689934237834846306337880590933233421966149769007479109112141725970988166803460403016361921878181423057339211671975644524743473186560152388343124951331038352989391477367028275936569768070265182131497010468036569230113617111521959062904754437132782906679686499791710292151740687493817244698976402306176493906157344851523812071236646815085587264348560861397087182868781389406784823717274127037546351723221467770480184043658935223200442374670828653506649486795021374158652766482329359262293393905180484929790630039893567627637040113635198519696653995786159059387056401082981242216697427303291871389338939966243487549043700404250641202299583379853373701976979839358942945723355601527286495101822501012557527894762802935901930898660341164213426053888440989263973332688121465354052968080649920064821773882347073572237438408919465038027110311068380795118386747459077780801731537707431857518223411944798537595386722692421630747920240354557942249789857262559172545706976981140483458845652796856121403828903646889976374178651163249018659264901464879700745270530265145955365854401998544118121430574241695275278734983168000000000000000000000000000000000", 
				ContarConfiguraciones2.contarConfiguraciones(arbol).toString());
		
	}

	@Test
	public void probarBusyBox() {

		IFeatureStructure arbol = cargarArbol("./modelos/busybox-1.18.0-model.xml");
		assertEquals("busybox-1.18.0-model.xml", 
				"773326650114693743667402296979004763697856824470597463869496546443225110967402406936162032193640200581305377827104114094843771431585073914394972953917748720261928064629663490129474355200000000000000000000000000000", 
				ContarConfiguraciones2.contarConfiguraciones(arbol).toString());
		
	}
	
	
	@Test
	public void probarAndroid60() {

		IFeatureStructure arbol = cargarArbol("./modelos/splot-android-60.xml");
		assertEquals("splot-android-60", 
				"90522780675807793014374400", 
				ContarConfiguraciones2.contarConfiguraciones(arbol).toString());
		
	}

	@Test
	public void probarAndroid510() {

		IFeatureStructure arbol = cargarArbol("./modelos/splot-android-510.xml");
		assertEquals("splot-android-510", 
				"17163143218631157208842240", 
				ContarConfiguraciones2.contarConfiguraciones(arbol).toString());
		
	}

	@Test
	public void probarUbuntu1204() {

		IFeatureStructure arbol = cargarArbol("./modelos/splot-ubuntu-1204.xml");
		assertEquals("splot-ubuntu-1204", 
				"1192239358188661194360904220672", 
				ContarConfiguraciones2.contarConfiguraciones(arbol).toString());
		
	}

	@Test
	public void probarUbuntu1410() {

		IFeatureStructure arbol = cargarArbol("./modelos/splot-ubuntu-1410.xml");
		assertEquals("splot-ubuntu-1410", 
				"30322943953881249675695893250048", 
				ContarConfiguraciones2.contarConfiguraciones(arbol).toString());
		
	}
	
	@Test
	public void probarWindows70() {

		IFeatureStructure arbol = cargarArbol("./modelos/splot-windows-70.xml");
		assertEquals("splot-windows-70", 
				"1515331070247134769369944680253630313394218624614400", 
				ContarConfiguraciones2.contarConfiguraciones(arbol).toString());
		
	}
	
	@Test
	public void probarWindows80() {

		IFeatureStructure arbol = cargarArbol("./modelos/splot-windows-80.xml");
		assertEquals("splot-windows-80", 
				"13049451256316164510349523186034251108754369566967068714250272768", 
				ContarConfiguraciones2.contarConfiguraciones(arbol).toString());
		
	}
	
}
