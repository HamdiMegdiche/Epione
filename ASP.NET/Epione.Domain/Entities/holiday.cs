namespace Epione.Domain
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("epione.holiday")]
    public partial class holiday
    {
        public int id { get; set; }

        [Column(TypeName = "date")]
        public DateTime? endDate { get; set; }

        [Column(TypeName = "date")]
        public DateTime? startDate { get; set; }

        public int? doctor_id { get; set; }

        public virtual user user { get; set; }
    }
}
