package nikolaou.christos.backend.order_processing;

import com.fasterxml.jackson.databind.ObjectMapper;
import lib.response.BackendResponse;
import nikolaou.christos.backend.order_processing.dto.OrderRequest;
import nikolaou.christos.backend.order_processing.dto.OrderResponse;
import nikolaou.christos.backend.order_processing.service.OrderService;
import nikolaou.christos.backend.order_processing.utils.OrderStatus;
import nikolaou.christos.backend.order_processing.utils.ShippingType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class,
excludeAutoConfiguration = {SecurityAutoConfiguration.class})
//@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private OrderService orderService;

    @Test
    void testProcessOrder_whenSuccessful_returns202() throws Exception {

        // Arrange
        String customerName = "customerName";
        double weight = 10.7;
        String destination = "destination";
        ShippingType shippingType = ShippingType.STANDARD;

        OrderRequest orderRequest = new OrderRequest(
                customerName,
                weight,
                destination,
                shippingType
        );

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderRequest));

        OrderResponse mockResponse = new OrderResponse(
                1L,
                customerName,
                weight,
                destination,
                shippingType,
                OrderStatus.PENDING,
                0.0
        );

        when(orderService.createOrder(orderRequest)).thenReturn(mockResponse);

        // Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andExpect(status().isAccepted()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        BackendResponse<OrderResponse> backendResponse = new ObjectMapper()
                .readValue(response,
                        new ObjectMapper().getTypeFactory()
                                .constructParametricType(BackendResponse.class, OrderResponse.class));

        OrderResponse orderResponse = backendResponse.data();

        // Assert
        assertNotNull(backendResponse);
        assertNotNull(orderResponse);
        assertEquals(backendResponse.message(), "Your order is currently being processed.", () -> "The backend response message is not the same.");
        assertEquals(customerName, orderResponse.customerName(), () -> "The expected customer name is not the same as the actual customer name.");
        assertEquals(weight, orderResponse.weight(), () -> "The expected weight is not the same as the actual weight.");
        assertEquals(destination, orderResponse.destination(), () -> "The expected destination is not the same as the actual destination.");
        assertEquals(shippingType, orderResponse.shippingType(), () -> "The expected shipping type is not the same as the actual shipping type.");

    }

    @Test
    void testGetOrderStatus_whenSuccessful_returns200() throws Exception {

        // Arrange
        Long orderId = 1L;
        OrderStatus orderStatus = OrderStatus.COMPLETED;
        OrderResponse orderResponse = new OrderResponse(
                1L,
                "customerName",
                10.7,
                "destination",
                ShippingType.STANDARD,
                orderStatus,
                100.0
        );

        when(orderService.getOrderDetails(orderId)).thenReturn(orderResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/orders/status/" + orderId)
                .contentType(MediaType.APPLICATION_JSON);

        // Act
        MvcResult result = mockMvc.perform(requestBuilder).andExpect(status().isOk()).andReturn();

        BackendResponse<OrderStatus> backendResponse = new ObjectMapper().readValue(
                result.getResponse().getContentAsString(),
                new ObjectMapper().getTypeFactory().constructParametricType(BackendResponse.class, OrderStatus.class));

        OrderStatus status = backendResponse.data();

        // Assert
        assertNotNull(backendResponse);
        assertNotNull(status);
        assertEquals(orderStatus, status, () -> "The status of the order is not the same.");

    }

    @Test
    void testGetOrderDetails_whenSuccessful_returnsOrderDetails() throws Exception {

        // Arrange
        Long orderId = 1L;
        OrderResponse mockResponse = new OrderResponse(
                orderId,
                "customerName",
                10.7,
                "destination",
                ShippingType.STANDARD,
                OrderStatus.COMPLETED,
                100.0
        );

        when(orderService.getOrderDetails(orderId)).thenReturn(mockResponse);

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/orders/"+orderId)).andReturn();
        String response = result.getResponse().getContentAsString();

        BackendResponse<OrderResponse> backendResponse = new ObjectMapper().readValue(response,
                new ObjectMapper().getTypeFactory().constructParametricType(BackendResponse.class, OrderResponse.class));

        OrderResponse actualResponse = backendResponse.data();

        // Assert
        assertNotNull(backendResponse);
        assertNotNull(actualResponse);
        assertEquals(backendResponse.message(), "Order Details Retrieved", () -> "The backend response message is not the same.");
        assertEquals(mockResponse, actualResponse, () -> "The backend response is not the same.");
    }

}