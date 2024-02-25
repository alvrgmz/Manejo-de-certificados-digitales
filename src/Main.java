import java.io.FileInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class Main {

    public static void main(String[] args) {
        String rutaArchivo = "certificado.crt"; // Ruta del archivo de certificado
        X509Certificate certificado = cargarCertificado(rutaArchivo);
        if (certificado != null) {
            mostrarInformacionCertificado(certificado);
            boolean esValido = verificarAutenticidadCertificado(certificado);
            System.out.println("El certificado es válido: " + esValido);
        } else {
            System.out.println("No se pudo cargar el certificado.");
        }
    }

    // Método para cargar un certificado desde un archivo
    public static X509Certificate cargarCertificado(String rutaArchivo) {
        try (FileInputStream fis = new FileInputStream(rutaArchivo)) {
            //Creamos una instancia de la clase CertificateFactory con el tipo de certificado que queremos cargar
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            //Retornamos el certificado cargado mediante el método de la clase CertificateFactory
            return (X509Certificate) cf.generateCertificate(fis);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para mostrar la información del certificado
    public static void mostrarInformacionCertificado(X509Certificate certificado) {
        System.out.println("Propietario: " + certificado.getSubjectDN());
        System.out.println("Emisor: " + certificado.getIssuerDN());
        System.out.println("Número de serie: " + certificado.getSerialNumber());
        System.out.println("Fecha de emisión: " + certificado.getNotBefore());
        System.out.println("Fecha de vencimiento: " + certificado.getNotAfter());
        System.out.println("Algoritmo de firma: " + certificado.getSigAlgName());
    }

    // Método para verificar la autenticidad del certificado
    public static boolean verificarAutenticidadCertificado(X509Certificate certificado) {
        try {
            certificado.verify(certificado.getPublicKey());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}