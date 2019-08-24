
package com.unibs.zanotti.inforinvestigador.domain.model.crossref;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Message {

    @SerializedName("indexed")
    @Expose
    private Indexed indexed;
    @SerializedName("reference-count")
    @Expose
    private Long referenceCount;
    @SerializedName("publisher")
    @Expose
    private String publisher;
    @SerializedName("issue")
    @Expose
    private String issue;
    @SerializedName("license")
    @Expose
    private List<License> license = null;
    @SerializedName("content-domain")
    @Expose
    private ContentDomain contentDomain;
    @SerializedName("short-container-title")
    @Expose
    private List<String> shortContainerTitle = null;
    @SerializedName("published-print")
    @Expose
    private PublishedPrint publishedPrint;
    @SerializedName("DOI")
    @Expose
    private String dOI;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("created")
    @Expose
    private Created created;
    @SerializedName("update-policy")
    @Expose
    private String updatePolicy;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("is-referenced-by-count")
    @Expose
    private Long isReferencedByCount;
    @SerializedName("title")
    @Expose
    private List<String> title = null;
    @SerializedName("prefix")
    @Expose
    private String prefix;
    @SerializedName("volume")
    @Expose
    private String volume;
    @SerializedName("author")
    @Expose
    private List<Author> author = null;
    @SerializedName("member")
    @Expose
    private String member;
    @SerializedName("published-online")
    @Expose
    private PublishedOnline publishedOnline;
    @SerializedName("reference")
    @Expose
    private List<Reference> reference = null;
    @SerializedName("container-title")
    @Expose
    private List<String> containerTitle = null;
    @SerializedName("original-title")
    @Expose
    private List<Object> originalTitle = null;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("link")
    @Expose
    private List<Link> link = null;
    @SerializedName("deposited")
    @Expose
    private Deposited deposited;
    @SerializedName("score")
    @Expose
    private Long score;
    @SerializedName("subtitle")
    @Expose
    private List<Object> subtitle = null;
    @SerializedName("short-title")
    @Expose
    private List<Object> shortTitle = null;
    @SerializedName("issued")
    @Expose
    private Issued issued;
    @SerializedName("references-count")
    @Expose
    private Long referencesCount;
    @SerializedName("journal-issue")
    @Expose
    private JournalIssue journalIssue;
    @SerializedName("alternative-id")
    @Expose
    private List<String> alternativeId = null;
    @SerializedName("URL")
    @Expose
    private String uRL;
    @SerializedName("relation")
    @Expose
    private Relation relation;
    @SerializedName("ISSN")
    @Expose
    private List<String> iSSN = null;
    @SerializedName("issn-type")
    @Expose
    private List<IssnType> issnType = null;
    @SerializedName("article-number")
    @Expose
    private String articleNumber;
    @SerializedName("abstract")
    @Expose
    private String paperAbstract;

    public String getAbstract() {
        return paperAbstract;
    }

    public String getdOI() {
        return dOI;
    }

    public void setdOI(String dOI) {
        this.dOI = dOI;
    }

    public String getuRL() {
        return uRL;
    }

    public void setuRL(String uRL) {
        this.uRL = uRL;
    }

    public List<String> getiSSN() {
        return iSSN;
    }

    public void setiSSN(List<String> iSSN) {
        this.iSSN = iSSN;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    public Indexed getIndexed() {
        return indexed;
    }

    public void setIndexed(Indexed indexed) {
        this.indexed = indexed;
    }

    public Long getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(Long referenceCount) {
        this.referenceCount = referenceCount;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public List<License> getLicense() {
        return license;
    }

    public void setLicense(List<License> license) {
        this.license = license;
    }

    public ContentDomain getContentDomain() {
        return contentDomain;
    }

    public void setContentDomain(ContentDomain contentDomain) {
        this.contentDomain = contentDomain;
    }

    public List<String> getShortContainerTitle() {
        return shortContainerTitle;
    }

    public void setShortContainerTitle(List<String> shortContainerTitle) {
        this.shortContainerTitle = shortContainerTitle;
    }

    public PublishedPrint getPublishedPrint() {
        return publishedPrint;
    }

    public void setPublishedPrint(PublishedPrint publishedPrint) {
        this.publishedPrint = publishedPrint;
    }

    public String getDOI() {
        return dOI;
    }

    public void setDOI(String dOI) {
        this.dOI = dOI;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Created getCreated() {
        return created;
    }

    public void setCreated(Created created) {
        this.created = created;
    }

    public String getUpdatePolicy() {
        return updatePolicy;
    }

    public void setUpdatePolicy(String updatePolicy) {
        this.updatePolicy = updatePolicy;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getIsReferencedByCount() {
        return isReferencedByCount;
    }

    public void setIsReferencedByCount(Long isReferencedByCount) {
        this.isReferencedByCount = isReferencedByCount;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public List<Author> getAuthor() {
        return author;
    }

    public void setAuthor(List<Author> author) {
        this.author = author;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public PublishedOnline getPublishedOnline() {
        return publishedOnline;
    }

    public void setPublishedOnline(PublishedOnline publishedOnline) {
        this.publishedOnline = publishedOnline;
    }

    public List<Reference> getReference() {
        return reference;
    }

    public void setReference(List<Reference> reference) {
        this.reference = reference;
    }

    public List<String> getContainerTitle() {
        return containerTitle;
    }

    public void setContainerTitle(List<String> containerTitle) {
        this.containerTitle = containerTitle;
    }

    public List<Object> getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(List<Object> originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Link> getLink() {
        return link;
    }

    public void setLink(List<Link> link) {
        this.link = link;
    }

    public Deposited getDeposited() {
        return deposited;
    }

    public void setDeposited(Deposited deposited) {
        this.deposited = deposited;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public List<Object> getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(List<Object> subtitle) {
        this.subtitle = subtitle;
    }

    public List<Object> getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(List<Object> shortTitle) {
        this.shortTitle = shortTitle;
    }

    public Issued getIssued() {
        return issued;
    }

    public void setIssued(Issued issued) {
        this.issued = issued;
    }

    public Long getReferencesCount() {
        return referencesCount;
    }

    public void setReferencesCount(Long referencesCount) {
        this.referencesCount = referencesCount;
    }

    public JournalIssue getJournalIssue() {
        return journalIssue;
    }

    public void setJournalIssue(JournalIssue journalIssue) {
        this.journalIssue = journalIssue;
    }

    public List<String> getAlternativeId() {
        return alternativeId;
    }

    public void setAlternativeId(List<String> alternativeId) {
        this.alternativeId = alternativeId;
    }

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public List<String> getISSN() {
        return iSSN;
    }

    public void setISSN(List<String> iSSN) {
        this.iSSN = iSSN;
    }

    public List<IssnType> getIssnType() {
        return issnType;
    }

    public void setIssnType(List<IssnType> issnType) {
        this.issnType = issnType;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

}
