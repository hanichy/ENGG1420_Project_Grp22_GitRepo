Campus Booking & Waitlist System
A robust Java-based application designed to manage campus resources, events, and user registrations. The system features an automated waitlist engine that ensures fair placement when event capacities are reached.

Key Features
User Management: Supports multiple roles (Students, Faculty, Admins).

Event Scheduling: Create events with defined physical or virtual capacities.

Automated Waitlist: When an event is full, users are automatically queued.

Instant Promotion: If a confirmed user cancels, the system automatically promotes the first person on the waitlist to "Confirmed" status.

Conflict Prevention: Prevents double-booking for individual users.

Prerequisites
Java Development Kit (JDK): Version 11 or higher.

IDE: IntelliJ IDEA (Community or Ultimate).

Build Tool: IntelliJ Native (or Maven/Gradle if applicable).

Setup & Execution in IntelliJ IDEA
Open the Project:

Launch IntelliJ IDEA.

Select Open and navigate to the project folder.

Configure the SDK:

Go to File > Project Structure > Project.

Select your installed JDK (e.g., Java 17).

Run the Application:

Locate the Main.java file in the src directory.

Click the Green Play Arrow in the gutter next to public static void main.

The system console will open at the bottom of the IDE.

Technical Architecture
Class Structure
The project follows a modular, layered architecture to separate concerns:

Models (com.campus.models): Contains User, Event, and Booking entities.

Services (com.campus.services): Contains the BookingManager and WaitlistService where the core business logic resides.

Data (com.campus.repository): Simulates a database using Collections (Lists/Maps) to store active data.

Design Patterns Used
Singleton Pattern: Applied to the BookingManager to ensure a single, synchronized instance manages all event capacities across the application.

Observer Logic: The system "observes" booking cancellations. When a spot opens up, the WaitlistService is triggered to update the status of the next eligible user.

FIFO Queue: The waitlist is implemented using a First-In-First-Out data structure to ensure absolute fairness in seat allocation.

When a cancellation occurs:

The system identifies the EventID.

It polls the WaitlistQueue for that specific event.

If a user exists in the queue, their status is updated from WAITLISTED to CONFIRMED immediately.
