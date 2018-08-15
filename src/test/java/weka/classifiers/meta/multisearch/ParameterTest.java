package weka.classifiers.meta.multisearch;

import junit.framework.TestCase;
import weka.core.Utils;
import weka.core.setupgenerator.MLPLayersParameter;

public class ParameterTest extends TestCase {

	public void testMLPLayersParam() throws Exception {
		MLPLayersParameter mlplayers = new MLPLayersParameter();
		String options = "-minLayers 1 -maxLayers 2 -minLayerSize 4 -maxLayerSize 8";
		mlplayers.setOptions(Utils.splitOptions(options));

		assertTrue(mlplayers.getMinLayers() == 1);
		assertTrue(mlplayers.getMaxLayers() == 2);
		assertTrue(mlplayers.getMinLayerSize() == 4);
		assertTrue(mlplayers.getMaxLayerSize() == 8);
		String[] items = mlplayers.getItems();
		int expectedSize = 30; // 5^1 + 5^2
		assertTrue(items.length == expectedSize);
	}
	

	public void testMLPLayersParamLarger() throws Exception {
		MLPLayersParameter mlplayers = new MLPLayersParameter();
		String options = "-minLayers 1 -maxLayers 4 -minLayerSize 9 -maxLayerSize 16";
		mlplayers.setOptions(Utils.splitOptions(options));

		assertTrue(mlplayers.getMinLayers() == 1);
		assertTrue(mlplayers.getMaxLayers() == 4);
		assertTrue(mlplayers.getMinLayerSize() == 9);
		assertTrue(mlplayers.getMaxLayerSize() == 16);
		String[] items = mlplayers.getItems();
		int expectedSize = 4680; // * 8^1 + 8^2 + 8^3 + 8^4
		assertTrue(items.length == expectedSize);
	}

	public void testMLPLayersParamIllegalLayerSizeRaises() {
		MLPLayersParameter mlplayers = new MLPLayersParameter();
		String options = "-minLayerSize 4 -maxLayerSize 2";
		try {
			mlplayers.setOptions(Utils.splitOptions(options));
			fail("expected error was not raised");
		} catch (Exception e) {
			String expected = "minLayerSize should be smaller than or equal to maxLayerSize";
			assertEquals(expected, e.getMessage()); 
		}
	}

	public void testMLPLayersParamIllegalLayersRaises() {
		MLPLayersParameter mlplayers = new MLPLayersParameter();
		String options = "-minLayers 4 -maxLayers 2";
		try {
			mlplayers.setOptions(Utils.splitOptions(options));
			fail("expected error was not raised");
		} catch (Exception e) {
			String expected = "minLayers should be smaller than or equal to maxLayers";
			assertEquals(expected, e.getMessage()); 
		}
	}

	public void testMLPLayersParamIllegalBothRaises() {
		MLPLayersParameter mlplayers = new MLPLayersParameter();
		String options = "-minLayers 2 -maxLayers 2 -minLayerSize 4 -maxLayerSize 4";
		try {
			mlplayers.setOptions(Utils.splitOptions(options));
			fail("expected error was not raised");
		} catch (Exception e) {
			String expected = "no variation in layer structure possible";
			assertEquals(expected, e.getMessage()); 
		}
	}
}
