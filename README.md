from reportlab.lib.pagesizes import letter
from reportlab.lib import colors
from reportlab.lib.units import inch
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle, Image
from reportlab.lib.styles import getSampleStyleSheet

# Create the PDF
pdf_path = "/mnt/data/Fragrance_Online_Shop_Noah_Bouffard_Design_Report.pdf"
doc = SimpleDocTemplate(pdf_path, pagesize=letter)
styles = getSampleStyleSheet()
elements = []

# Cover page
elements.append(Paragraph("<b>Fragrance Online Shop – Noah Bouffard</b>", styles["Title"]))
elements.append(Spacer(1, 0.3 * inch))
elements.append(Paragraph("Student ID: 2431848", styles["Normal"]))
elements.append(Paragraph("Course: 420-N34-LA Java Web Programming", styles["Normal"]))
elements.append(Paragraph("Professor: Haikel Hichri", styles["Normal"]))
elements.append(Paragraph("Term: Fall 2025", styles["Normal"]))
elements.append(Paragraph("GitHub Repository: <a href='https://github.com/Noah-tdf/Fragrance-Online-Shop-Noah-Bouffard'>https://github.com/Noah-tdf/Fragrance-Online-Shop-Noah-Bouffard</a>", styles["Normal"]))
elements.append(Spacer(1, 0.5 * inch))

# Introduction
elements.append(Paragraph("<b>1. Introduction</b>", styles["Heading2"]))
intro_text = """
The Fragrance Online Shop is a RESTful Spring Boot backend application that manages customers, their perfume orders, and products.
It is designed for administrators to view and maintain product inventory, track customer orders, and perform CRUD operations on Products, Orders, and Customers.
This backend uses a 3-layer Spring Boot architecture with an H2 in-memory database and REST-compliant endpoints.
"""
elements.append(Paragraph(intro_text, styles["Normal"]))
elements.append(Spacer(1, 0.2 * inch))

# Resources table
elements.append(Paragraph("<b>2. Resources and Data Model</b>", styles["Heading2"]))
data = [
    ["Entity", "Fields (Type)", "Description"],
    ["Customer", "id: Long, firstName: String, lastName: String, email: String, address: String", "A registered client"],
    ["Order", "id: Long, orderDate: LocalDate, totalAmount: double, customer: Customer, products: List<Product>", "A purchase record linked to a customer"],
    ["Product", "id: Long, name: String, brand: String, description: String, price: double, fragranceNotes: String, gender: String, order: Order", "A perfume product in stock"]
]
t = Table(data, colWidths=[1.2 * inch, 3.0 * inch, 2.5 * inch])
t.setStyle(TableStyle([('BACKGROUND', (0, 0), (-1, 0), colors.grey),
                       ('TEXTCOLOR', (0, 0), (-1, 0), colors.whitesmoke),
                       ('ALIGN', (0, 0), (-1, -1), 'LEFT'),
                       ('FONTNAME', (0, 0), (-1, 0), 'Helvetica-Bold'),
                       ('GRID', (0, 0), (-1, -1), 0.5, colors.black)]))
elements.append(t)
elements.append(Spacer(1, 0.3 * inch))

# UML Diagram placeholder
elements.append(Paragraph("<b>3. UML Class Diagram</b>", styles["Heading2"]))
uml_text = "Customer (1) → Order (1) → Product (many)\nEach class includes typical attributes and relationships using @OneToMany and @ManyToOne annotations."
elements.append(Paragraph(uml_text, styles["Normal"]))
elements.append(Spacer(1, 0.2 * inch))

# ERD Diagram placeholder
elements.append(Paragraph("<b>4. ERD (Entity Relationship Diagram)</b>", styles["Heading2"]))
erd_text = "Customer (1) ───< Order (1) ───< Product\nForeign Keys:\n• customer_id in Order\n• order_id in Product"
elements.append(Paragraph(erd_text, styles["Normal"]))
elements.append(Spacer(1, 0.3 * inch))

# Endpoints
elements.append(Paragraph("<b>5. REST Endpoints</b>", styles["Heading2"]))
endpoint_data = [
    ["Resource", "Method", "Path", "Description"],
    ["Product", "GET", "/api/products", "Retrieve all products"],
    ["Product", "GET", "/api/products/{id}", "Retrieve a single product"],
    ["Product", "POST", "/api/products", "Add new product"],
    ["Product", "DELETE", "/api/products/{id}", "Delete product"],
    ["Order", "GET", "/api/orders", "Retrieve all orders"],
    ["Order", "GET", "/api/orders/{id}", "Retrieve single order"],
    ["Order", "POST", "/api/orders", "Add new order"],
    ["Order", "DELETE", "/api/orders/{id}", "Delete order"],
    ["Customer", "GET", "/api/customers", "Retrieve all customers"],
    ["Customer", "GET", "/api/customers/{id}", "Retrieve single customer"],
    ["Customer", "POST", "/api/customers", "Add new customer"],
    ["Customer", "DELETE", "/api/customers/{id}", "Delete customer"]
]
end_table = Table(endpoint_data, colWidths=[1.1 * inch, 0.9 * inch, 2.5 * inch, 2.6 * inch])
end_table.setStyle(TableStyle([('BACKGROUND', (0, 0), (-1, 0), colors.grey),
                               ('TEXTCOLOR', (0, 0), (-1, 0), colors.whitesmoke),
                               ('GRID', (0, 0), (-1, -1), 0.25, colors.black),
                               ('ALIGN', (0, 0), (-1, -1), 'LEFT')]))
elements.append(end_table)
elements.append(Spacer(1, 0.3 * inch))

# Validation plan
elements.append(Paragraph("<b>6. Validation Plan</b>", styles["Heading2"]))
validation_text = """
• Customer.email → @Email, @NotBlank
• Customer.firstName & lastName → @NotBlank, @Size(min = 2)
• Product.name, brand → @NotBlank
• Product.price, Order.totalAmount → @Positive
Bad input returns HTTP 400 Bad Request.
"""
elements.append(Paragraph(validation_text, styles["Normal"]))
elements.append(Spacer(1, 0.3 * inch))

# Wireframes description
elements.append(Paragraph("<b>7. Wireframes</b>", styles["Heading2"]))
wireframe_desc = """
The following mockups represent key screens for a future frontend implementation:
1. Product List Page – Displays available perfumes with name, brand, and price.
2. Customer List Page – Displays customer names, emails, and addresses.
3. Order Details Page – Shows products associated with a specific order.
"""
elements.append(Paragraph(wireframe_desc, styles["Normal"]))
elements.append(Spacer(1, 0.3 * inch))

# Non-functional considerations
elements.append(Paragraph("<b>8. Non-Functional Considerations</b>", styles["Heading2"]))
nonfunc = """
• Error Handling: Global @ControllerAdvice for 404 and 400 responses.
• Naming Conventions: Follows feature-based packages (DataAccessLayer, BusinessLogicLayer, PresentationLayer).
• Testing: Postman collections for CRUD and relationship endpoints.
• Deployment: Ready for Render or Railway hosting in Milestone 2.
"""
elements.append(Paragraph(nonfunc, styles["Normal"]))
elements.append(Spacer(1, 0.3 * inch))

# Rubric
elements.append(Paragraph("<b>9. Grading Rubric (Part 1)</b>", styles["Heading2"]))
rubric_data = [
    ["Criterion", "Description", "Points"],
    ["System Description", "Clear and concise introduction", "1"],
    ["Resources + UML/ERD", "Accurate entity and relationship design", "2"],
    ["Endpoints list", "REST naming conventions followed", "1"],
    ["Wireframes + Validation", "Demonstrated planning", "1"],
    ["Total", "", "5"]
]
rubric_table = Table(rubric_data, colWidths=[2.5 * inch, 3.3 * inch, 0.6 * inch])
rubric_table.setStyle(TableStyle([('BACKGROUND', (0, 0), (-1, 0), colors.grey),
                                  ('TEXTCOLOR', (0, 0), (-1, 0), colors.whitesmoke),
                                  ('GRID', (0, 0), (-1, -1), 0.25, colors.black)]))
elements.append(rubric_table)
elements.append(Spacer(1, 0.3 * inch))

# Footer
elements.append(Paragraph("<b>Author:</b> Noah Bouffard – 2431848", styles["Normal"]))
elements.append(Paragraph("<b>Date:</b> October 2025", styles["Normal"]))
elements.append(Paragraph("<b>GitHub Repository:</b> https://github.com/Noah-tdf/Fragrance-Online-Shop-Noah-Bouffard", styles["Normal"]))

# Build PDF
doc.build(elements)
pdf_path

