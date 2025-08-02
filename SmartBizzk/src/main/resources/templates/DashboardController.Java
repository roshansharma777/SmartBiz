package SmartBizzk.SmartBizzk.controller;

import SmartBizzk.SmartBizzk.entity.*;
import SmartBizzk.SmartBizzk.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private SalesInvoiceService salesInvoiceService;

    @Autowired
    private PartyService partyService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PaymentInService paymentInService;

    @Autowired
    private RecordService recordService; // Assuming this is for expenses

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        try {
            // Calculate Total Revenue from Sales Invoices
            List<SalesInvoice> allInvoices = salesInvoiceService.getAllSalesInvoices();
            BigDecimal totalRevenue = allInvoices.stream()
                    .map(SalesInvoice::getTotalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Count Total Orders (Sales Invoices + Purchases)
            long totalSalesOrders = allInvoices.size();
            long totalPurchaseOrders = purchaseService.getAllPurchases().size();
            long totalOrders = totalSalesOrders + totalPurchaseOrders;

            // Count Active Customers (Total Parties)
            long activeCustomers = partyService.getAllParties().size();

            // Count Total Products (Items)
            long totalProducts = itemService.getAllItems().size();

            // Recent Activity Data (Last 5 entries)
            List<SalesInvoice> recentSalesInvoices = salesInvoiceService.getRecentSalesInvoices(5);
            List<PaymentIn> recentPaymentsIn = paymentInService.getRecentPayments(5);
            List<Record> recentExpenses = recordService.getRecentRecords(5); // Assuming Record entity is for expenses

            // Add data to model
            model.addAttribute("totalRevenue", totalRevenue);
            model.addAttribute("totalOrders", totalOrders);
            model.addAttribute("activeCustomers", activeCustomers);
            model.addAttribute("totalProducts", totalProducts);
            
            // Recent activity
            model.addAttribute("recentSalesInvoices", recentSalesInvoices);
            model.addAttribute("recentPaymentsIn", recentPaymentsIn);
            model.addAttribute("recentExpenses", recentExpenses);

        } catch (Exception e) {
            // Log error and set default values
            System.err.println("Error loading dashboard data: " + e.getMessage());
            model.addAttribute("totalRevenue", BigDecimal.ZERO);
            model.addAttribute("totalOrders", 0L);
            model.addAttribute("activeCustomers", 0L);
            model.addAttribute("totalProducts", 0L);
        }

        return "dashboard";
    }
}
