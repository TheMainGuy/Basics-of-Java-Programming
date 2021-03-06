package hr.fer.zemris.java.raytracer.model;

public class Sphere extends GraphicalObject {

  Point3D center;
  double radius;
  double kdr;
  double kdg;
  double kdb;
  double krr;
  double krg;
  double krb;
  double krn;

  public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
      double krn) {
    super();
    this.center = center;
    this.radius = radius;
    this.kdr = kdr;
    this.kdg = kdg;
    this.kdb = kdb;
    this.krr = krr;
    this.krg = krg;
    this.krb = krb;
    this.krn = krn;
  }

  @Override
  public RayIntersection findClosestRayIntersection(Ray ray) {
    Point3D rayStart = ray.start;
    Point3D direction = ray.direction;
    if(rayStart.sub(center).norm() < radius) {
      return null;
    }
    double b = direction.scalarProduct(rayStart.sub(center));
    try {
      double root = Math.sqrt(Math.pow(b, 2) - Math.pow(rayStart.sub(center).norm(), 2) + radius * radius);
      if(Double.isNaN(root)) {
        return null;
      }
      double distance = 0;
      double distance1 = -b + root;
      double distance2 = -b - root;
      distance = (distance1 < distance2) ? distance1 : distance2;
      return new RayIntersectionImpl(rayStart.add(direction.scalarMultiply(distance)), distance, true);
    } catch (Exception e) {
      return null;
    }

  }

  private class RayIntersectionImpl extends RayIntersection {

    protected RayIntersectionImpl(Point3D point, double distance, boolean outer) {
      super(point, distance, outer);
    }

    @Override
    public Point3D getNormal() {
      return (center.sub(getPoint()));
    }

    @Override
    public double getKdr() {
      return kdr;
    }

    @Override
    public double getKdg() {
      return kdg;
    }

    @Override
    public double getKdb() {
      return kdb;
    }

    @Override
    public double getKrr() {
      return krr;
    }

    @Override
    public double getKrg() {
      return krg;
    }

    @Override
    public double getKrb() {
      return krb;
    }

    @Override
    public double getKrn() {
      return krn;
    }

  }

}
