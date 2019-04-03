package sample;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;

public class Controller {

    //Connection variables depend on your machine and MySql!
    String hostName = null;
    String port = null;
    String password = null;


    @FXML
    ListView createListView;
    @FXML
    ListView billingListView;
    @FXML
    ListView<String> billListView;
    @FXML
    ListView<String> billListViewII;
    @FXML
    Tab billingTab;
    @FXML
    Tab createTab;
    @FXML
    HBox billHBox;
    @FXML
    VBox billVBox;
    @FXML
    VBox billVBoxII;
    @FXML
    VBox billVBoxIII;
    @FXML
    Pane billPane;
    @FXML
    Button transferButton;
    @FXML
    TableView searchTable;
    @FXML
    Button searchButton;
    @FXML
    javafx.scene.control.TableColumn buildingColumn;
    @FXML
    javafx.scene.control.TableColumn billedColumn;
    @FXML
    javafx.scene.control.TableColumn paidColumn;
    @FXML
    javafx.scene.control.TableColumn billToColumn;
    @FXML
    javafx.scene.control.TableColumn dateColumn;
    @FXML
    javafx.scene.control.TableColumn priceColumn;

    /**
     * Initializer for the GUI, called in Main.java
     * Here we initialize the listview selections to dictate the Anchorpane for selections...
     */
    protected  void Init()
    {
        searchTable.setEditable(true);
        buildingColumn.setCellValueFactory(new PropertyValueFactory<InvoiceII, Integer>("buildingID"));
        billedColumn.setCellValueFactory(new PropertyValueFactory<InvoiceII, Boolean>("billed"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<InvoiceII, Double>("totalPrice"));
        billToColumn.setCellValueFactory(new PropertyValueFactory<InvoiceII, Integer>("bill_To"));
        paidColumn.setCellValueFactory(new PropertyValueFactory<InvoiceII, Boolean>("paid"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<InvoiceII, String>("date"));

        priceColumn.setCellFactory(TextFieldTableCell.<InvoiceII, Double>forTableColumn(new DoubleStringConverter()));
        priceColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<InvoiceII, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<InvoiceII, Double> event) {
                ((InvoiceII) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())
                ).setTotalPrice(event.getNewValue());


            }
        });
        //Sets a listener to billingListView to do an action upon selection of item.
        //Checks for conditions such as null values, and if the corresponding Tab is selected before executing task.
        //Then runs thru a logical operation to execute a task.
        billingListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue != null && billingTab.isSelected())
                {
                    System.out.println("Selected Item: " + newValue + " Type of: " + newValue.getClass());

                    if(newValue == "Bill")
                    {
                        billHBox.setVisible(true);
                        billVBox.setVisible(true);
                        billListView.setItems(populateBillListView());
                    }
                    else if (newValue == "Search")
                    {
                        ObservableList<String> items = FXCollections.observableArrayList("Test");
                        billListView.setItems(items);
                    }
                }
            }
        });

        createListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue != null && createTab.isSelected())
                    System.out.println("Selected Item: " + newValue + " Type of: " + newValue.getClass());
            }
        });

        billListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        billListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue != null && billingTab.isSelected())
                {
                    billPane.setVisible(true);
                    billVBoxII.setVisible(true);
                    billVBoxIII.setVisible(true);
                    /*
                    ObservableList<String> selectedItems = billListView.getSelectionModel().getSelectedItems();

                    System.out.println("Selected Items:");
                    for(Object o : selectedItems)
                    {
                        System.out.println(o + "\n");
                    }*/
                }
            }
        });


    }

    /**
     * loads create list view with items upon opening tab.
     * @param Event
     */
    @FXML
    protected void loadCreate(Event Event)
    {
        ObservableList<String> items = FXCollections.observableArrayList ("Customer", "Property", "Invoice", "Service", "Material");
        createListView.setItems(items);

    }

    /***
     * loads billing list view with items upon opening tab.
     * @param Event
     */
    @FXML
    protected void loadBilling(Event Event)
    {
        ObservableList<String> items = FXCollections.observableArrayList ("Bill", "Search");
        billingListView.setItems(items);
        billHBox.setVisible(false);
        billVBox.setVisible(false);
        billPane.setVisible(false);
        billVBoxII.setVisible(false);
        billVBoxIII.setVisible(false);
    }

    //button action to transfer to the billing queue
    @FXML
    protected void transfer(Event event)
    {

        ObservableList<String> items = billListViewII.getItems();

        ObservableList<String> selectedItems = billListView.getSelectionModel().getSelectedItems();

        for(Object o : selectedItems)
        {
            items.add(o.toString());
        }

        billListViewII.setItems(items);


    }

    /***
     * Deletes items from Queue - Prevents from submitting bill
     * @param e
     */
    @FXML
    protected void deleteQueue(KeyEvent e){
        if(e.getCode() == KeyCode.D){
            ObservableList<String> items = billListViewII.getItems();
            ObservableList<Integer> selectedIndices = billListViewII.getSelectionModel().getSelectedIndices();
            ObservableList<String> selectedItems = billListViewII.getSelectionModel().getSelectedItems();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Delete");
            alert.setHeaderText("Are you sure you want to delete the selected item(s)?");
            alert.setContentText(selectedItems.toString());
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    for(int o : selectedIndices){
                        System.out.println("Removing:  " + items.get(o));
                        items.remove(o);

                    }
                }
            });
        }
    }

    @FXML
    protected void submitBills(ActionEvent event)
    {
        // Invoice invoice = new Invoice();
        PdfMaker pdf = new PdfMaker();
        pdf.MakePDF("Test");
    }

    /**
     * creates records in Invoices_Serviced
     */
    private void generateInvoiced(){

    }

    //Used to return a list to populate the BillListView in Billing Tab
    private ObservableList<String> populateBillListView()
    {
        ObservableList<String> items;

        //SQL QUERY, dont forget Try/Catch clauses to prevent our program from crashing!!!!!

        //example
        items = FXCollections.observableArrayList("Hello","From","DB");

        return  items;
    }
    //action to search in database
    @FXML
    protected  void searchButtonClicked(ActionEvent Event)
    {

        loadSearchListView();
    }


    @FXML
    protected void editTotalPrice(TableColumn.CellEditEvent<InvoiceII, Double> event){
        System.out.println(
                ((InvoiceII)event.getTableView().getItems().get(
                event.getTablePosition().getRow())
        ).getTotalPrice());
    }
    //This will take in a observablelist<Invoices>
    private void loadSearchListView(){
        try{
            ObservableList<InvoiceII> data = FXCollections.observableArrayList(
                    new InvoiceII(123, false, 250.00, 12, false, "12/12/2012"),
                    new InvoiceII(13, false, 230.00, 12, true, "12/12/2012")
            );


            for(InvoiceII i : data){
                System.out.println(i.getTotalPrice());
            }

            searchTable.setItems(data);
            //searchTable.getItems().addAll(buildingColumn, billedColumn, priceColumn,billToColumn,paidColumn, dateColumn);

        }catch(Exception e)
        {
            System.out.println(e.getStackTrace()[0].getLineNumber());
        }


    }


    /*
    @FXML
    Button billingButton;

    @FXML
    VBox leftVBox;

    @FXML
    HBox topHBox;

    @FXML
    protected void viewCreate(ActionEvent Event)
    {
        System.out.println("Create Clicked");
        leftVBox.setVisible(true);

    }

    @FXML
    protected void viewBilling(ActionEvent Event)
    {
        System.out.println("Billing Clicked");
    }

    @FXML
    protected void submit(ActionEvent Event)
    {
        System.out.println("Submit Clicked");
    }
*/



}
