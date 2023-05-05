
package com.company.restclient.ui;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.company.restclient.core.RestClient;
import com.company.restclient.dto.CustomerDTO;
import com.company.restclient.model.CustomerModel;
import com.company.restclient.util.ItemMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rapidclipse.framework.server.resources.CaptionUtils;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;


@Route("")
public class MainLayout extends VerticalLayout implements PageConfigurator
{
	public MainLayout()
	{
		super();
		this.initUI();
	}

	@Override
	public void configurePage(final InitialPageSettings settings)
	{
		settings.addLink("shortcut icon", "frontend/images/favicon.ico");
		settings.addFavIcon("icon", "frontend/images/favicon256.png", "256x256");
	}

	/**
	 * Event handler delegate method for the {@link Button} {@link #button}.
	 *
	 * @see ComponentEventListener#onComponentEvent(ComponentEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void button_onClick(final ClickEvent<Button> event)
	{
		final WebTarget target   = RestClient.getWebTarget();
		final Response  response = target.path("customer").request().get(Response.class);
		
		if(response.getStatus() == 200)
		{
			final String json = response.readEntity(String.class);
			final Type   type = TypeToken.getParameterized(ArrayList.class, CustomerDTO.class).getType();
			
			final ArrayList<CustomerDTO> customers = new Gson().fromJson(json, type);
			
			this.grid.setDataProvider(DataProvider.ofCollection(ItemMapper.fromList(customers, CustomerModel.class)));
		}
		else
		{
			System.out.println(response.getStatus());
		}
	}
	
	/**
	 * Event handler delegate method for the {@link Button} {@link #button2}.
	 *
	 * @see ComponentEventListener#onComponentEvent(ComponentEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void button2_onClick(final ClickEvent<Button> event)
	{
		final WebTarget target   = RestClient.getWebTarget();
		final Response  response = target.path("customer")
			.path(this.txtCustomerId.getValue())
			.request()
			.get(Response.class);

		if(response.getStatus() == 200)
		{
			final String      json     = response.readEntity(String.class);
			final CustomerDTO customer = new Gson().fromJson(json, CustomerDTO.class);
			
			final CustomerModel customerModel = ItemMapper.fromItem(customer, CustomerModel.class);
			
			this.grid.setDataProvider(DataProvider.ofCollection(Arrays.asList(customerModel)));
			return;
		}
		
		if(response.getStatus() == 404)
		{
			System.out.println(response.getStatus() + " " + response.readEntity(String.class));
			return;
		}

		System.out.println(response.getStatus());
	}
	
	/**
	 * Event handler delegate method for the {@link Button} {@link #button3}.
	 *
	 * @see ComponentEventListener#onComponentEvent(ComponentEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void button3_onClick(final ClickEvent<Button> event)
	{
		final CustomerDTO customer = new CustomerDTO();
		customer.setCompanyname("Müller GmbH");
		customer.setContactname("Sebastian");
		customer.setContacttitle("");
		customer.setAddress("Postweg. 55");
		customer.setCity("Weiden");
		customer.setRegion("");
		customer.setPostalcode("92637");
		customer.setCountry("Deutschland");
		customer.setPhone("123456");
		customer.setFax("654321");

		final WebTarget target   = RestClient.getWebTarget();
		final Response  response = target.path("customer")
			.request()
			.post(Entity.entity(customer, MediaType.APPLICATION_JSON));

		if(response.getStatus() == 201)
		{
			final String      json        = response.readEntity(String.class);
			final CustomerDTO customerDto = new Gson().fromJson(json, CustomerDTO.class);

			System.out.println("customer created with id : " + customerDto.getCustomerid());
		}
	}
	
	/* WARNING: Do NOT edit!<br>The content of this method is always regenerated by the UI designer. */
	// <generated-code name="initUI">
	private void initUI()
	{
		this.horizontalLayout = new HorizontalLayout();
		this.button           = new Button();
		this.button2          = new Button();
		this.txtCustomerId    = new TextField();
		this.button3          = new Button();
		this.grid             = new Grid<>(CustomerModel.class, false);

		this.button.setText("Find all customers");
		this.button2.setText("Find customer by id");
		this.button3.setText("Create customer");
		this.grid.addColumn(CustomerModel::getCustomerid).setKey("customerid")
			.setHeader(CaptionUtils.resolveCaption(CustomerModel.class, "customerid")).setSortable(true);
		this.grid.addColumn(CustomerModel::getCountry).setKey("country")
			.setHeader(CaptionUtils.resolveCaption(CustomerModel.class, "country")).setSortable(true);
		this.grid.addColumn(CustomerModel::getCompanyname).setKey("companyname")
			.setHeader(CaptionUtils.resolveCaption(CustomerModel.class, "companyname")).setSortable(true);
		this.grid.addColumn(CustomerModel::getContactname).setKey("contactname")
			.setHeader(CaptionUtils.resolveCaption(CustomerModel.class, "contactname")).setSortable(true);
		this.grid.addColumn(CustomerModel::getContacttitle).setKey("contacttitle")
			.setHeader(CaptionUtils.resolveCaption(CustomerModel.class, "contacttitle")).setSortable(true);
		this.grid.addColumn(CustomerModel::getCity).setKey("city")
			.setHeader(CaptionUtils.resolveCaption(CustomerModel.class, "city")).setSortable(true);
		this.grid.addColumn(CustomerModel::getAddress).setKey("address")
			.setHeader(CaptionUtils.resolveCaption(CustomerModel.class, "address")).setSortable(true);
		this.grid.setSelectionMode(Grid.SelectionMode.SINGLE);

		this.button.setSizeUndefined();
		this.button2.setSizeUndefined();
		this.txtCustomerId.setSizeUndefined();
		this.button3.setSizeUndefined();
		this.horizontalLayout.add(this.button, this.button2, this.txtCustomerId, this.button3);
		this.horizontalLayout.setWidthFull();
		this.horizontalLayout.setHeight(null);
		this.grid.setSizeFull();
		this.add(this.horizontalLayout, this.grid);
		this.setFlexGrow(1.0, this.grid);
		this.setSizeFull();

		this.button.addClickListener(this::button_onClick);
		this.button2.addClickListener(this::button2_onClick);
		this.button3.addClickListener(this::button3_onClick);
	} // </generated-code>
	
	// <generated-code name="variables">
	private Button              button, button2, button3;
	private Grid<CustomerModel> grid;
	private HorizontalLayout    horizontalLayout;
	private TextField           txtCustomerId;
	// </generated-code>

}
