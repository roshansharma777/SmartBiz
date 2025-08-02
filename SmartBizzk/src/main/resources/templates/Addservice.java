// Add these methods to your respective Service Implementation classes

// In SalesInvoiceServiceImpl.java
@Override
public List<SalesInvoice> getRecentSalesInvoices(int limit) {
    return salesInvoiceRepository.findTop5ByOrderByInvoiceDateDesc();
    // Or if you want to use Pageable:
    // Pageable pageable = PageRequest.of(0, limit);
    // return salesInvoiceRepository.findAllByOrderByInvoiceDateDesc(pageable).getContent();
}

// In PaymentInService.java (add to interface)
List<PaymentIn> getRecentPayments(int limit);

// In PaymentInServiceImpl.java (or wherever you implement PaymentInService)
@Override
public List<PaymentIn> getRecentPayments(int limit) {
    return paymentInRepository.findTop5ByOrderByPaymentDateDesc();
}

// In RecordService.java (add to interface) - assuming Record is for expenses
List<Record> getRecentRecords(int limit);

// In RecordServiceImpl.java
@Override
public List<Record> getRecentRecords(int limit) {
    return recordsRepository.findTop5ByOrderByDateDesc();
}

// Add these methods to your Repository interfaces:

// In SalesInvoiceRepository.java
@Query("SELECT s FROM SalesInvoice s ORDER BY s.invoiceDate DESC")
List<SalesInvoice> findTop5ByOrderByInvoiceDateDesc();

// In PaymentInRepository.java  
@Query("SELECT p FROM PaymentIn p ORDER BY p.paymentDate DESC")
List<PaymentIn> findTop5ByOrderByPaymentDateDesc();

// In RecordsRepository.java
@Query("SELECT r FROM Record r ORDER BY r.date DESC")
List<Record> findTop5ByOrderByDateDesc();

// Alternative using method naming convention (if you prefer):
// List<SalesInvoice> findTop5ByOrderByInvoiceDateDesc();
// List<PaymentIn> findTop5ByOrderByPaymentDateDesc(); 
// List<Record> findTop5ByOrderByDateDesc();
