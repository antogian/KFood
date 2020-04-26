function openModal(id)
{
    $.ajax
    ({
        url: "/item/" + id,
        success: function (data)
        {
            $("#itemViewModal").append(data);
            $("#itemViewModal").modal("show");
        }
    });



    // /*<![CDATA[*/
    //
    // var currentItem = /*[[${selectedItem}]]*/ 'default';
    // var categories = /*[[${allCats}]]*/ 'default';
    //
    // console.log(categories.length);
    //
    // for (var cat in categories)
    // {
    //     console.log(cat.getName)
    //     // for (var item in cat.getAllItems())
    //     // {
    //     //     if(id.equals(item.getId()))
    //     //     {
    //     //         currentItem = item;
    //     //     }
    //     // }
    // }
    //
    // /*]]>*/
}
