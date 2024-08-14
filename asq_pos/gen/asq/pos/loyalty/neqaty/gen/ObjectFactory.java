
package asq.pos.loyalty.neqaty.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the asq.pos.loyalty.neqaty.gen package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AuthorizeReq_QNAME = new QName("", "req");
    private final static QName _ConfirmRequestTransactionReference_QNAME = new QName("", "transactionReference");
    private final static QName _ConfirmRequestAuthenticationKey_QNAME = new QName("", "authenticationKey");
    private final static QName _ConfirmRequestMsisdn_QNAME = new QName("", "msisdn");
    private final static QName _ConfirmRequestTid_QNAME = new QName("", "tid");
    private final static QName _ConfirmRequestOneTimePassword_QNAME = new QName("", "oneTimePassword");
    private final static QName _AuthorizeResponseResultDescription_QNAME = new QName("", "resultDescription");
    private final static QName _AuthorizeResponseOptionsData_QNAME = new QName("", "optionsData");
    private final static QName _AuthorizeRequestOperationType_QNAME = new QName("", "operationType");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: asq.pos.loyalty.neqaty.gen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Confirm }
     * 
     */
    public Confirm createConfirm() {
        return new Confirm();
    }

    /**
     * Create an instance of {@link ConfirmRequest }
     * 
     */
    public ConfirmRequest createConfirmRequest() {
        return new ConfirmRequest();
    }

    /**
     * Create an instance of {@link Abort }
     * 
     */
    public Abort createAbort() {
        return new Abort();
    }

    /**
     * Create an instance of {@link AbortRequest }
     * 
     */
    public AbortRequest createAbortRequest() {
        return new AbortRequest();
    }

    /**
     * Create an instance of {@link AbortResponse }
     * 
     */
    public AbortResponse createAbortResponse() {
        return new AbortResponse();
    }

    /**
     * Create an instance of {@link Authorize }
     * 
     */
    public Authorize createAuthorize() {
        return new Authorize();
    }

    /**
     * Create an instance of {@link AuthorizeRequest }
     * 
     */
    public AuthorizeRequest createAuthorizeRequest() {
        return new AuthorizeRequest();
    }

    /**
     * Create an instance of {@link AuthorizeResponse }
     * 
     */
    public AuthorizeResponse createAuthorizeResponse() {
        return new AuthorizeResponse();
    }

    /**
     * Create an instance of {@link NeqatyWSAPIOptionsData }
     * 
     */
    public NeqatyWSAPIOptionsData createNeqatyWSAPIOptionsData() {
        return new NeqatyWSAPIOptionsData();
    }

    /**
     * Create an instance of {@link ConfirmResponse }
     * 
     */
    public ConfirmResponse createConfirmResponse() {
        return new ConfirmResponse();
    }

    /**
     * Create an instance of {@link NeqatyWSAPIRedeemOption }
     * 
     */
    public NeqatyWSAPIRedeemOption createNeqatyWSAPIRedeemOption() {
        return new NeqatyWSAPIRedeemOption();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthorizeRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "req", scope = Authorize.class)
    public JAXBElement<AuthorizeRequest> createAuthorizeReq(AuthorizeRequest value) {
        return new JAXBElement<AuthorizeRequest>(_AuthorizeReq_QNAME, AuthorizeRequest.class, Authorize.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "transactionReference", scope = ConfirmRequest.class)
    public JAXBElement<String> createConfirmRequestTransactionReference(String value) {
        return new JAXBElement<String>(_ConfirmRequestTransactionReference_QNAME, String.class, ConfirmRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "authenticationKey", scope = ConfirmRequest.class)
    public JAXBElement<String> createConfirmRequestAuthenticationKey(String value) {
        return new JAXBElement<String>(_ConfirmRequestAuthenticationKey_QNAME, String.class, ConfirmRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "msisdn", scope = ConfirmRequest.class)
    public JAXBElement<String> createConfirmRequestMsisdn(String value) {
        return new JAXBElement<String>(_ConfirmRequestMsisdn_QNAME, String.class, ConfirmRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tid", scope = ConfirmRequest.class)
    public JAXBElement<Integer> createConfirmRequestTid(Integer value) {
        return new JAXBElement<Integer>(_ConfirmRequestTid_QNAME, Integer.class, ConfirmRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "oneTimePassword", scope = ConfirmRequest.class)
    public JAXBElement<String> createConfirmRequestOneTimePassword(String value) {
        return new JAXBElement<String>(_ConfirmRequestOneTimePassword_QNAME, String.class, ConfirmRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfirmRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "req", scope = Confirm.class)
    public JAXBElement<ConfirmRequest> createConfirmReq(ConfirmRequest value) {
        return new JAXBElement<ConfirmRequest>(_AuthorizeReq_QNAME, ConfirmRequest.class, Confirm.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "transactionReference", scope = AuthorizeResponse.class)
    public JAXBElement<String> createAuthorizeResponseTransactionReference(String value) {
        return new JAXBElement<String>(_ConfirmRequestTransactionReference_QNAME, String.class, AuthorizeResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "resultDescription", scope = AuthorizeResponse.class)
    public JAXBElement<String> createAuthorizeResponseResultDescription(String value) {
        return new JAXBElement<String>(_AuthorizeResponseResultDescription_QNAME, String.class, AuthorizeResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NeqatyWSAPIOptionsData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "optionsData", scope = AuthorizeResponse.class)
    public JAXBElement<NeqatyWSAPIOptionsData> createAuthorizeResponseOptionsData(NeqatyWSAPIOptionsData value) {
        return new JAXBElement<NeqatyWSAPIOptionsData>(_AuthorizeResponseOptionsData_QNAME, NeqatyWSAPIOptionsData.class, AuthorizeResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "transactionReference", scope = AuthorizeRequest.class)
    public JAXBElement<String> createAuthorizeRequestTransactionReference(String value) {
        return new JAXBElement<String>(_ConfirmRequestTransactionReference_QNAME, String.class, AuthorizeRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "operationType", scope = AuthorizeRequest.class)
    public JAXBElement<String> createAuthorizeRequestOperationType(String value) {
        return new JAXBElement<String>(_AuthorizeRequestOperationType_QNAME, String.class, AuthorizeRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "authenticationKey", scope = AuthorizeRequest.class)
    public JAXBElement<String> createAuthorizeRequestAuthenticationKey(String value) {
        return new JAXBElement<String>(_ConfirmRequestAuthenticationKey_QNAME, String.class, AuthorizeRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "msisdn", scope = AuthorizeRequest.class)
    public JAXBElement<String> createAuthorizeRequestMsisdn(String value) {
        return new JAXBElement<String>(_ConfirmRequestMsisdn_QNAME, String.class, AuthorizeRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tid", scope = AuthorizeRequest.class)
    public JAXBElement<Integer> createAuthorizeRequestTid(Integer value) {
        return new JAXBElement<Integer>(_ConfirmRequestTid_QNAME, Integer.class, AuthorizeRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "resultDescription", scope = ConfirmResponse.class)
    public JAXBElement<String> createConfirmResponseResultDescription(String value) {
        return new JAXBElement<String>(_AuthorizeResponseResultDescription_QNAME, String.class, ConfirmResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NeqatyWSAPIOptionsData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "optionsData", scope = ConfirmResponse.class)
    public JAXBElement<NeqatyWSAPIOptionsData> createConfirmResponseOptionsData(NeqatyWSAPIOptionsData value) {
        return new JAXBElement<NeqatyWSAPIOptionsData>(_AuthorizeResponseOptionsData_QNAME, NeqatyWSAPIOptionsData.class, ConfirmResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "resultDescription", scope = AbortResponse.class)
    public JAXBElement<String> createAbortResponseResultDescription(String value) {
        return new JAXBElement<String>(_AuthorizeResponseResultDescription_QNAME, String.class, AbortResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "transactionReference", scope = AbortRequest.class)
    public JAXBElement<String> createAbortRequestTransactionReference(String value) {
        return new JAXBElement<String>(_ConfirmRequestTransactionReference_QNAME, String.class, AbortRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "authenticationKey", scope = AbortRequest.class)
    public JAXBElement<String> createAbortRequestAuthenticationKey(String value) {
        return new JAXBElement<String>(_ConfirmRequestAuthenticationKey_QNAME, String.class, AbortRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "msisdn", scope = AbortRequest.class)
    public JAXBElement<String> createAbortRequestMsisdn(String value) {
        return new JAXBElement<String>(_ConfirmRequestMsisdn_QNAME, String.class, AbortRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "tid", scope = AbortRequest.class)
    public JAXBElement<Integer> createAbortRequestTid(Integer value) {
        return new JAXBElement<Integer>(_ConfirmRequestTid_QNAME, Integer.class, AbortRequest.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AbortRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "req", scope = Abort.class)
    public JAXBElement<AbortRequest> createAbortReq(AbortRequest value) {
        return new JAXBElement<AbortRequest>(_AuthorizeReq_QNAME, AbortRequest.class, Abort.class, value);
    }

}
