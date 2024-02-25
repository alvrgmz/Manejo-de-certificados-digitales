import java.io.FileInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class Main {

    public static void main(String[] args) {
        String filePath = "certificado.crt"; // Ruta del archivo de certificado
        X509Certificate certificate = cargarCertificado(filePath);
        if (certificate != null) {
            mostrarInformacionCertificado(certificate);
            boolean esValido = verificarAutenticidadCertificado(certificate);
            System.out.println("El certificado es válido: " + esValido);
        } else {
            System.out.println("No se pudo cargar el certificado.");
        }
    }

    // Método para cargar un certificado desde un archivo
    public static X509Certificate cargarCertificado(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            return (X509Certificate) cf.generateCertificate(fis);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para mostrar la información del certificado
    public static void mostrarInformacionCertificado(X509Certificate certificate) {
        System.out.println("Propietario: " + certificate.getSubjectDN());
        System.out.println("Emisor: " + certificate.getIssuerDN());
        System.out.println("Número de serie: " + certificate.getSerialNumber());
        System.out.println("Fecha de emisión: " + certificate.getNotBefore());
        System.out.println("Fecha de vencimiento: " + certificate.getNotAfter());
        System.out.println("Algoritmo de firma: " + certificate.getSigAlgName());
    }

    // Método para verificar la autenticidad del certificado
    public static boolean verificarAutenticidadCertificado(X509Certificate certificate) {
        try {
            certificate.verify(certificate.getPublicKey());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}