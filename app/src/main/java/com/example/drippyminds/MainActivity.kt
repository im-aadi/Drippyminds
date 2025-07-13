package com.example.drippyminds

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drippyminds.ui.theme.DrippyMindsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrippyMindsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FreelancingPlatform()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreelancingPlatform() {
    var selectedTab by remember { mutableStateOf(0) }
    
    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        TopAppBar(
            title = { Text("Drippy Minds", fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
        
        // Tab Row
        TabRow(selectedTabIndex = selectedTab) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Services") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Reviews") }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = { Text("Orders") }
            )
        }
        
        // Content based on selected tab
        when (selectedTab) {
            0 -> ServicesTab()
            1 -> ReviewsTab()
            2 -> OrdersTab()
        }
    }
}

@Composable
fun ServicesTab() {
    val services = listOf(
        Service("Assignment Help", "Get help with academic assignments", "$20-50", "4.8/5"),
        Service("Coding Projects", "Custom software development", "$100-500", "4.9/5"),
        Service("Freelancing", "Professional freelance services", "$50-200", "4.7/5"),
        Service("Research Papers", "Academic research and writing", "$80-300", "4.6/5"),
        Service("Web Development", "Full-stack web applications", "$200-1000", "4.9/5"),
        Service("Mobile Apps", "iOS and Android development", "$300-1500", "4.8/5")
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(services) { service ->
            ServiceCard(service = service)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun ServiceCard(service: Service) {
    var showOrderDialog by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = service.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = service.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Price: ${service.price}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Rating: ${service.rating}",
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { showOrderDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Place Order")
            }
        }
    }
    
    if (showOrderDialog) {
        OrderDialog(
            service = service,
            onDismiss = { showOrderDialog = false }
        )
    }
}

@Composable
fun ReviewsTab() {
    val reviews = listOf(
        Review("John D.", "Assignment Help", "Excellent work! Got an A+ on my assignment.", 5),
        Review("Sarah M.", "Coding Project", "Amazing developer, delivered exactly what I needed.", 5),
        Review("Mike R.", "Research Paper", "High-quality research and well-written paper.", 4),
        Review("Lisa K.", "Web Development", "Professional service, highly recommended!", 5),
        Review("David P.", "Mobile App", "Great communication and timely delivery.", 4),
        Review("Emma W.", "Freelancing", "Very satisfied with the work quality.", 5)
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(reviews) { review ->
            ReviewCard(review = review)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun ReviewCard(review: Review) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.customerName,
                    fontWeight = FontWeight.Bold
                )
                Row {
                    repeat(review.rating) {
                        Text("⭐", fontSize = 16.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = review.service,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = review.comment,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun OrdersTab() {
    val orders = listOf(
        Order("ORD-001", "Assignment Help", "In Progress", "$35"),
        Order("ORD-002", "Coding Project", "Completed", "$250"),
        Order("ORD-003", "Research Paper", "Pending", "$120"),
        Order("ORD-004", "Web Development", "In Progress", "$800"),
        Order("ORD-005", "Mobile App", "Completed", "$1200")
    )
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(orders) { order ->
            OrderCard(order = order)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun OrderCard(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = order.orderId,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = order.amount,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = order.service,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Status: ${order.status}",
                fontSize = 12.sp,
                color = when (order.status) {
                    "Completed" -> MaterialTheme.colorScheme.primary
                    "In Progress" -> MaterialTheme.colorScheme.tertiary
                    else -> MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}

@Composable
fun OrderDialog(service: Service, onDismiss: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var requirements by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Place Order - ${service.name}") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Your Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = requirements,
                    onValueChange = { requirements = it },
                    label = { Text("Project Requirements") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Here you would typically send the order
                    onDismiss()
                }
            ) {
                Text("Submit Order")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

// Data classes
data class Service(
    val name: String,
    val description: String,
    val price: String,
    val rating: String
)

data class Review(
    val customerName: String,
    val service: String,
    val comment: String,
    val rating: Int
)

data class Order(
    val orderId: String,
    val service: String,
    val status: String,
    val amount: String
) 