package com.donation.service;

import static com.donation.model.TransactionType.CANCEL;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import com.donation.entity.Address;
import com.donation.entity.Designation;
import com.donation.entity.Donation;
import com.donation.entity.DonationEntry;
import com.donation.entity.DonationEntryDesignation;
import com.donation.entity.DonationEntryTransaction;
import com.donation.entity.Donor;
import com.donation.entity.LetterAddress;
import com.donation.entity.Suffix;
import com.donation.entity.Title;
import com.donation.entity.TransactionDetail;
import com.donation.entity.TransactionMapping;
import com.donation.exception.DuplicateEntityException;
import com.donation.exception.InvalidEntityException;
import com.donation.exception.InvalidOrderIdException;
import com.donation.model.DataMapping;
import com.donation.model.FieldError;
import com.donation.model.ImageMapper;
import com.donation.model.Mail;
import com.donation.repository.AddressRepository;
import com.donation.repository.DesignationCodeRepository;
import com.donation.repository.DesignationRepository;
import com.donation.repository.DonationEntryDesignationRepository;
import com.donation.repository.DonationEntryRepository;
import com.donation.repository.DonationEntryTrasactionRepository;
import com.donation.repository.DonationRepository;
import com.donation.repository.DonorRepository;
import com.donation.repository.LetterAddressRepository;
import com.donation.repository.OrderSequenceRepository;
import com.donation.repository.SuffixRepository;
import com.donation.repository.TitleRepository;
import com.donation.repository.TransactionDetailRepository;
import com.donation.repository.TransactionMappingRepository;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
public class TransactionDetailService {

	public static final Logger LOGGER = LoggerFactory.getLogger(TransactionDetailService.class);
	@Autowired
	private TransactionDetailRepository transactionDetailRepository;
	@Autowired
	private OrderSequenceRepository orderSequenceRepository;
	@Autowired
	private Environment environment;
	@Autowired
	private EmailHtmlSender emailHtmlSender;

	@Autowired
	private DonorRepository donorRepository;

	@Autowired
	private DonationRepository donationRepository;

	@Autowired
	private DesignationRepository designationRepository;

	@Autowired
	private DonationEntryService donationEntryService;
	
	@Autowired
	private TransactionMappingRepository transactionMappingRepository;
	
	@Autowired
	private DonationEntryRepository donationEntryRepository;
	

	@Autowired
	private DonationEntryTrasactionRepository donationEntryTrasactionRepository;
	
	@Autowired
	private DonationEntryDesignationRepository donationEntryDesignationRepository;
	
	@Autowired
	private LetterAddressRepository letterAddressRepository;
	
	@Autowired
	private SuffixRepository suffixRepository;
	
	@Autowired
	private TitleRepository titleRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private DesignationCodeRepository designationCodeRepository;
	

	private String dName;
	
	
	/**
	 * First Validate the entity object . In case, any error found it will throw
	 * {@link InvalidEntityException}. Next check is whether there is record with
	 * same orderId. If found then this method will throw
	 * {@link DuplicateEntityException} exception.
	 */
	public TransactionDetail saveTransactionDetail(TransactionDetail transactionDetail) {
		LOGGER.debug("Validating the  transactionDetail: {}", transactionDetail);
		FieldError validate = transactionDetail.validate();
		if (validate.hasError()) {
			LOGGER.debug("Error found inside entity :: {}", validate.getErrors());
			throw new InvalidEntityException(validate.getErrors());
		}
		if (orderSequenceRepository.findOne(transactionDetail.getOrderId()) == null) {
			throw new InvalidOrderIdException("Invalid order id");
		}
		LOGGER.debug("Going to store transaction details : {}", transactionDetail);
		if (transactionDetailRepository.exists(transactionDetail.getOrderId())) {

			LOGGER.debug("Already Existing Record is : {}",
					transactionDetailRepository.findOne(transactionDetail.getOrderId()));
			// throw new DuplicateEntityException(String.format("Already record exist
			// against order id %s", transactionDetail.getOrderId()));
		}

		TransactionDetail savedTransactionDetail = transactionDetailRepository.save(transactionDetail.markAsSuccess());

		LOGGER.debug("Updated Or Saved Record is : {}",
				transactionDetailRepository.findOne(transactionDetail.getOrderId()));

		// changed approved to string
		System.out.println(" Approved Value : " + savedTransactionDetail.getApproved());
		if (savedTransactionDetail.getApproved().equals("1")) {

			List<Designation> designations = designationRepository.findAll();

			System.out.println(" Complete DesignationList" + designations);

			designations.stream().forEach((designation) -> {
				// System.out.println("Designation " + designation.getOrderId());
				if (designation.getOrderId().equals(savedTransactionDetail.getOrderId().toString())) {
					// System.out.println("MatchED Designation " + designation);
					if (designation.getDesignationName() != null) {
						dName = designation.getDesignationName();
					} else {
						dName = "";
						LOGGER.debug("Designation Not found for OrderID :" + savedTransactionDetail.getOrderId());
					}
					// System.out.println("DMANE" +dName);

				}
			});
			// System.out.println("Saved Transaction OrderID " +
			// savedTransactionDetail.getOrderId());

			// System.out.println(" DESIGNATION VALUE : " +
			// designation.getDesignationName());

			Donor donor = donorRepository.findByOrderId(savedTransactionDetail.getOrderId());

			if (donor == null || isEmpty(donor.getEmail())) {
				LOGGER.warn("No donor mail id is present with respect to order id {}",
						savedTransactionDetail.getOrderId());
				return savedTransactionDetail;
			}
			List<ImageMapper> imageFiles = Arrays.asList(ImageMapper.builder().mapperName("imageResourceName")
					.imageFileName("email-banner.jpg").contentType("image/jpeg").build());
			Context context = new Context(Locale.forLanguageTag("en"));
			context.setVariable("title", "UPMC Email Thank You");
			context.setVariable("firstName", donor.getFirstName());
			context.setVariable("lastName", donor.getLastName());
			context.setVariable("designationName", dName);
			context.setVariable("donationAmount", savedTransactionDetail.getPaymentTotal());
			context.setVariable("transactionId", savedTransactionDetail.getTransactionId());
			imageFiles.forEach(
					imageMapper -> context.setVariable(imageMapper.getMapperName(), imageMapper.getImageFileName()));
			context.setVariable("imageResourceName", "email-banner.jpg");
			Mail mail = Mail.builder().recipient(donor.getEmail()).subject("UPMC Email Thank You")
					.imageMappers(imageFiles).build();
			emailHtmlSender.send(mail, "email/email-template", context);
		}
		
	  	try {
				doMapping(savedTransactionDetail);
			}
			catch (Exception e) {
				 System.out.println(e.getMessage());
					LOGGER.debug("");
			 }
//			finally {
//				return savedTransactionDetail;
//			}
	
		return savedTransactionDetail;
		
	}


	/**
	 * This method accept orderId and mark the transaction as cancel
	 */
	public TransactionDetail cancelTransactionDetail(String orderId) {

		TransactionDetail transactionDetail;

		LOGGER.info("Storing cancel transaction for order Id : {}", orderId);

		if (StringUtils.isBlank(orderId) || orderSequenceRepository.getOne(orderId) == null) {
			throw new InvalidOrderIdException("Invalid order id");
		}
		if (transactionDetailRepository.exists(orderId)) {
			transactionDetail = transactionDetailRepository.findOne(orderId);
			// throw new DuplicateEntityException(String.format("Already record exist
			// against order id %s", orderId));
		} else {

			transactionDetail = TransactionDetail.builder().transactionType(CANCEL).approved("-1").orderId(orderId)
					.build();
		}

		return transactionDetailRepository.save(transactionDetail);
	}

	/**
	 * This method accept orderId and deletes that transaction id which has cancel
	 */
	public void deleteTransactionDetail(String orderId) {

		TransactionDetail transactionDetail;

		LOGGER.info("Deleting cancel record present in the database : {}", orderId);

		if (StringUtils.isBlank(orderId) || orderSequenceRepository.getOne(orderId) == null) {
			throw new InvalidOrderIdException("Invalid order id");
		}
		if (transactionDetailRepository.exists(orderId)) {
			transactionDetailRepository.delete(orderId);
			// throw new DuplicateEntityException(String.format("Already record exist
			// against order id %s", orderId));
		}

		// return transactionDetailRepository.save(transactionDetail);
	}

	/**
	 * Reads all the entry from the {@link TransactionDetail} table and reads the
	 * last entry and increment by 1 to get new transaction id. In case, there is no
	 * transaction order id present id in the database, first time it will read from
	 * application.properties file
	 */
	public String generateNewOrderId() {
		LOGGER.debug("Retrieving last order id from the database.");
		Optional<List<String>> optional = transactionDetailRepository.getOrderIdsInDescendingOrder();
		if (optional.isPresent()) {
			List<String> stringList = optional.get();
			if (!stringList.isEmpty()) {
				return String.valueOf(Long.valueOf(stringList.get(0)) + 1);
			}
		}
		return environment.getProperty("transaction.initialOrderId");
	}

	public TransactionDetail verifyOrderID(String orderId) {
		return transactionDetailRepository.findOne(orderId);
	}
	
	/**
	 * doMapping()
	 * @param savedTransactionDetail
	 */
	
	private void doMapping(TransactionDetail savedTransactionDetail) {
		
		Donor donor = donorRepository.findByOrderId(savedTransactionDetail.getOrderId());
		
		Designation designation = designationRepository.findByOrderId(savedTransactionDetail.getOrderId());
		
		Donation donation = donationRepository.findByOrderId(savedTransactionDetail.getOrderId());
		
		Suffix suffix = suffixRepository.findByDisplayvalue(donor.getSuffix()!=null?donor.getSuffix():"Jr");
	
		Title  title  = titleRepository.findByDisplayvalue(donor.getTitle()!=null?donor.getTitle():"Mr.");
		
		TransactionMapping transactionMapping = TransactionMapping.builder()
				.order_id(savedTransactionDetail.getOrderId())
				.donationentryid(donationEntryService.getDonationEntrySequence().intValue())
				.build();
		transactionMappingRepository.save(transactionMapping);

		//enter into donationentry
		// INSERT INTO LETTERADDRESS with id here
		
		DataMapping dataMapping = DataMapping.builder()
				.OrderID(transactionMapping.getOrder_id())
				.DonationEntryID(transactionMapping.getDonationentryid())
				.DonationEntryTransactionID(donationEntryService.getDonationEntrySequence().intValue())
				.DonationEntryDesignationID(donationEntryService.getDonationEntrySequence().intValue())
				.AddressID(donationEntryService.getDonationEntrySequence().intValue())
				.LetterAddressID(donationEntryService.getDonationEntrySequence().intValue())
				.build();
													
		Address address = Address.builder()
				.addressid(dataMapping.getAddressID())
				.addressline1(donor.getStreetAddress1()!=null ? donor.getStreetAddress1().trim() : "")
				.addressline2(donor.getStreetAddress2()!=null ? donor.getStreetAddress2().trim() : "")
				.addressline3(donor.getStreetAddress3()!=null ? donor.getStreetAddress3().trim() : "")
				.city(donor.getCity().trim())
				.stateprovince(donor.getState().trim())
				.postalcode(donor.getZip().trim())
				.country(donor.getCountry().trim())
				.build();
		
		try {
			addressRepository.save(address);	
		}
		catch(Exception e) {
			System.out.println("Address Record" + e.getMessage());
			LOGGER.debug("Address Record" + dataMapping.getAddressID() );
		}
		
		// validate nullcheck for trim
		LetterAddress letterAddress = LetterAddress.builder()
				.letteraddressid(dataMapping.getLetterAddressID())
				.firstname(donor.getFirstName().trim())
				.lastname(donor.getLastName().trim())
				.titleid(title.getTitleid())
				.suffixid(suffix.getSuffixid())
				.addressid(address.getAddressid())
				.build();
		
		letterAddressRepository.save(letterAddress);
		
		// change contactmethod later
		DonationEntry donationEntry = DonationEntry.builder()
				.donationentryid(transactionMapping.getDonationentryid())
				.donationamount(donation.getDollarAmount())
				.donationinfoanonymous(donation.isAnonymous())
				.inmemoryof(donation.getInMemoryOf())
				.inhonorof(donation.getInHonorOf())
				.sendletter(donation.isNotify())
				.firstname(donor.getFirstName().trim())
				.lastname(donor.getLastName().trim())
				.phonenumber(donor.getPhone())
				.faxnumber(donor.getFax())
				.emailaddress(donor.getEmail())
				.donorinfoanonymous(donation.isAnonymous())
				.createdate(new Date() )
				.exported(new Boolean(false))
				.exportdate(new Date())
				.letteraddressid(letterAddress.getLetteraddressid())
				.titleid(title.getTitleid())
				.suffixid(suffix.getSuffixid())
				.addressid(address.getAddressid())
				.contactmethodid(8)
				.build();

				donationEntryRepository.save(donationEntry);
		
		DonationEntryTransaction donationEntryTransaction = DonationEntryTransaction.builder()
				.donationentrytransactionid(dataMapping.getDonationEntryTransactionID())
				.upaytransactionid(savedTransactionDetail.getTransactionId().trim())
				.upayresultcode("Approved")
				.transactiontype(3)
				.recieptinfo("9999")
				.upayfullresult(123L)
				.donationentryid(transactionMapping.getDonationentryid())
				.cardtype("NA")
				.build();

		donationEntryTrasactionRepository.save(donationEntryTransaction);
		
		
		DonationEntryDesignation donationEntryDesignation = DonationEntryDesignation.builder()
				.donationentrydesignationid(dataMapping.getDonationEntryDesignationID())
				.percentage(100.00f)
				.designationamount(donation.getDollarAmount())
				.donationentryid(transactionMapping.getDonationentryid())
				.designationid(designationCodeRepository.findByDesignationname(designation.getDesignationName()).getDesignationid())
				.build();
	
		try {
			donationEntryDesignationRepository.save(donationEntryDesignation);
		}
		catch(Exception e) {
			System.out.println("Error while inserting into DonationEntryDesignation :: " + e.getMessage() + ":: " + donationEntryDesignation);
			LOGGER.debug("DonationEntryDesignation");
		}
		
		// End Mappinp
		
		
	}
}
