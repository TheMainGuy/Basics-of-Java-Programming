package hr.fer.zemris.java.raytracer;

import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Implements drawing of 3D scenes with lighting. Phong reflextion model is used
 * for object lightning.
 * 
 * @author tin
 *
 */
public class RayCaster {
  /**
   * Method which is called when program runs.
   * 
   * @param args command line arguments
   */
  public static void main(String[] args) {
    RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0), new Point3D(0, 0, 10),
        20, 20);
  }

  /**
   * Implements {@link IRayTracerProducer} object. Draws image of objects
   * initialized in scene. Objects are lit by a number of lights. Each light
   * affects each object it can reach. Light is implemented using Phong reflextion
   * model.
   * 
   * @return {@link IRayTracerProducer} object
   */
  private static IRayTracerProducer getIRayTracerProducer() {
    return new IRayTracerProducer() {
      @Override
      public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width,
          int height, long requestNo, IRayTracerResultObserver observer) {
        System.out.println("Započinjem izračune...");
        short[] red = new short[width * height];
        short[] green = new short[width * height];
        short[] blue = new short[width * height];
        Point3D og = view.sub(eye).normalize();
        Point3D vuv = viewUp.normalize();
        Point3D yAxis = vuv.sub(og.scalarMultiply(og.scalarProduct(vuv))).normalize();
        Point3D xAxis = og.vectorProduct(yAxis).normalize();
        // Point3D zAxis = yAxis.vectorProduct(xAxis);
        Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2)).add(yAxis.scalarMultiply(vertical / 2));
        Scene scene = RayTracerViewer.createPredefinedScene();
        short[] rgb = new short[3];
        int offset = 0;
        for (int y = 0; y < height; y++) {
          for (int x = 0; x < width; x++) {
            Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(horizontal * x / (width - 1.0)))
                .sub(yAxis.scalarMultiply(vertical * y / (height - 1.0)));
            Ray ray = Ray.fromPoints(eye, screenPoint);
            tracer(scene, ray, rgb);
            red[offset] = rgb[0] > 255 ? 255 : rgb[0];
            green[offset] = rgb[1] > 255 ? 255 : rgb[1];
            blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
            offset++;
          }
        }
        System.out.println("Izračuni gotovi...");
        observer.acceptResult(red, green, blue, requestNo);
        System.out.println("Dojava gotova...");
      }

      /**
       * Method used for defining color for pixel from given ray and scene. Alters
       * given rgb pointer in order to give back information.
       * 
       * @param scene scene upon user looks
       * @param ray ray which specifies where to take color from
       * @param rgb rgb pointer which is altered
       */
      private void tracer(Scene scene, Ray ray, short[] rgb) {
        double[] rgb2 = new double[3];
        RayIntersection firstIntersection = firstIntersection(scene, ray);
        if(firstIntersection == null) {
          rgb[0] = 0;
          rgb[1] = 0;
          rgb[2] = 0;
        } else {
          rgb2 = determineColorFor(firstIntersection, scene, ray);
          rgb[0] = (short) rgb2[0];
          rgb[1] = (short) rgb2[1];
          rgb[2] = (short) rgb2[2];
        }
      }

      /**
       * Determines color for an intersection from given scene, ray and intersection.
       * For intersection to be lit by a light, there can not be any other objects
       * between that light and intersection.
       * 
       * @param firstIntersection intersection whose color will be determined
       * @param scene scene of objects and lights
       * @param ray ray from user to intersection
       * @return array of 3 rgb doubles, 1 for each color
       */
      private double[] determineColorFor(RayIntersection firstIntersection, Scene scene, Ray ray) {
        double[] rgb = new double[3];
        rgb[0] = 15;
        rgb[1] = 15;
        rgb[2] = 15;

        for (LightSource light : scene.getLights()) {
          Ray lightRay = Ray.fromPoints(light.getPoint(), firstIntersection.getPoint());
          RayIntersection lightIntersection = firstIntersection(scene, lightRay);

          if(lightIntersection != null
              && firstIntersection.getPoint().sub(lightIntersection.getPoint()).norm() <= 0.01) {
            Point3D n = lightIntersection.getNormal().normalize();
            Point3D l = lightIntersection.getPoint().sub(light.getPoint()).normalize();
            double cos = n.scalarProduct(l);
            cos = cos < 0 ? 0 : cos;
            rgb[0] += light.getR() * lightIntersection.getKdr() * cos;
            rgb[1] += light.getG() * lightIntersection.getKdg() * cos;
            rgb[2] += light.getB() * lightIntersection.getKdb() * cos;

            Point3D v = lightIntersection.getPoint().sub(ray.start).normalize();
            Point3D r = l.negate().sub(n.scalarMultiply(l.negate().scalarProduct(n) * 2));
            double cos2 = v.scalarProduct(r);
            cos2 = cos2 < 0 ? 0 : cos2;
            rgb[0] += light.getR() * lightIntersection.getKrr() * Math.pow(cos2, lightIntersection.getKrn());
            rgb[1] += light.getG() * lightIntersection.getKrg() * Math.pow(cos2, lightIntersection.getKrn());
            rgb[2] += light.getB() * lightIntersection.getKrb() * Math.pow(cos2, lightIntersection.getKrn());
          }
        }
        return rgb;
      }

      /**
       * Helper method which finds the first intersection between given ray and object
       * in given scene.
       * 
       * @param scene scene
       * @param ray ray
       * @return first intersection
       */
      private RayIntersection firstIntersection(Scene scene, Ray ray) {
        List<GraphicalObject> graphicalObjects = scene.getObjects();
        double minDistance = -1;
        RayIntersection firstIntersection = null;
        for (GraphicalObject graphicalObject : graphicalObjects) {
          RayIntersection intersection = graphicalObject.findClosestRayIntersection(ray);
          if(intersection != null) {
            double distance = intersection.getDistance();
            if(distance < minDistance || minDistance == -1) {
              minDistance = distance;
              firstIntersection = intersection;
            }
          }
        }
        return firstIntersection;
      }
    };
  }
}
